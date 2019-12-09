package com.Backend.service;

import com.Backend.domain.JustJoinIT;
import com.Backend.dto.TechnologyDto;
import com.Backend.entity.City;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.CityOffers;
import com.Backend.entity.offers.CountryOffers;
import com.Backend.entity.offers.TechnologyOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyOffersRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScrapService {

    private ModelMapper modelMapper;
    private RequestService requestService;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersRepository technologyOffersRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public ScrapService(ModelMapper modelMapper, RequestService requestService, TechnologyRepository technologyRepository,
                        TechnologyOffersRepository technologyOffersRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(technologyMapping);
        this.modelMapper.addConverter(totalConverter);
        this.requestService = Objects.requireNonNull(requestService);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyOffersRepository = Objects.requireNonNull(technologyOffersRepository);
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    private PropertyMap<TechnologyOffers, TechnologyDto> technologyMapping = new PropertyMap<TechnologyOffers, TechnologyDto>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
            map().setId(source.getTechnology().getId());
            using(totalConverter).map(map().getTotal());
        }
    };

    private Converter<Integer, Integer> totalConverter = context -> {
        TechnologyOffers technology = (TechnologyOffers) context.getParent().getSource();
        return technology.getLinkedin() + technology.getPracuj() + technology.getNoFluffJobs() + technology.getJustJoinIT();
    };

    public List<TechnologyDto> getTechnologyStatistics(String city) {

        List<TechnologyOffers> list = technologyOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(city).orElse(null));

        if(list.isEmpty()){
            return scrapTechnologyStatistics(city);
        } else {
            return list.stream().map(technology -> modelMapper.map(technology, TechnologyDto.class)).collect(Collectors.toList());
        }
    }

    public List<CountryOffers> scrapTechnologyStatisticsForCountries(String country) {

        String selectedCountryUTF8 = country.toLowerCase();
        List<CountryOffers> countriesOffers = new ArrayList<>();
        List<Technology> technologies = technologyRepository.findAll();
        Optional<Country> countryOptional = countryRepository.findCountryByName(selectedCountryUTF8);
        UrlBuilder urlBuilder = new UrlBuilder();

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();

            String linkedinUrl = urlBuilder.linkedinBuildUrlForCityAndCountry(selectedTechnology, selectedCountryUTF8);
            String indeedUrl = urlBuilder.indeedBuildUrlForCountry(selectedTechnology, countryOptional.get().getCode());

            CountryOffers countryOffers = new CountryOffers(countryOptional.orElse(null), technology, LocalDate.now());

            try {
                countryOffers.setIndeed(requestService.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            countryOffers.setLinkedin(requestService.scrapLinkedinOffers(linkedinUrl));

            countriesOffers.add(countryOffers);
        });

        return countriesOffers;
    }

    public List<TechnologyDto> scrapTechnologyStatistics(String cityName) {
        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestService.removePolishSigns(cityNameUTF8).toLowerCase();
        List<Technology> technologies = technologyRepository.findAll();
        List<CityOffers> citiesOffers = new ArrayList<>();
        City city = cityRepository.findCityByName(cityNameUTF8);
        UrlBuilder urlBuilder = new UrlBuilder();

        technologies.forEach(technology -> {

            String technologyName = technology.getName().toLowerCase();

            String linkedinDynamicURL = urlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedDynamicURL = urlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujDynamicURL = urlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsDynamicURL = urlBuilder.noFluffJobsBuildUrlForCity();

            CityOffers cityOffers = new CityOffers(city, technology, LocalDate.now());

            try {
                cityOffers.setIndeed(requestService.scrapIndeedOffers(indeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }

            cityOffers.setLinkedin(requestService.scrapLinkedinOffers(linkedinDynamicURL));
            cityOffers.setPracuj(requestService.scrapPracujOffers(pracujDynamicURL));
            cityOffers.setNoFluffJobs(requestService.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));
            cityOffers.setJustJoinIT(requestService.scrapJustJoin(city, technology));

            citiesOffers.add(cityOffers);
        });



        return city
                .filter(ignoredCity -> !technologyOffersRepository.existsFirstByDateAndCity(LocalDate.now(), ignoredCity))
                .map(ignoredCity -> citiesOffers.stream().map(category -> modelMapper.map(technologyOffersRepository.save(category), TechnologyDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> citiesOffers.stream().map(category -> modelMapper.map(category, TechnologyDto.class)).collect(Collectors.toList()));
    }

}
