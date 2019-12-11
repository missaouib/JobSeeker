package com.Backend.service;

import com.Backend.dto.CityDto;
import com.Backend.dto.CountryDto;
import com.Backend.entity.City;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyCityOffers;
import com.Backend.entity.offers.TechnologyCountryOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyCityOffersRepository;
import com.Backend.repository.offers.TechnologyCountryOffersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScrapTechnologyService {

    private ModelMapper modelMapper;
    private MapperService mapperService;
    private RequestService requestService;
    private TechnologyRepository technologyRepository;
    private TechnologyCityOffersRepository technologyCityOffersRepository;
    private TechnologyCountryOffersRepository technologyCountryOffersRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public ScrapTechnologyService(ModelMapper modelMapper, RequestService requestService, TechnologyRepository technologyRepository,
                                  CityRepository cityRepository, CountryRepository countryRepository, MapperService mapperService,
                                  TechnologyCountryOffersRepository technologyCountryOffersRepository, TechnologyCityOffersRepository technologyCityOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.mapperService = Objects.requireNonNull(mapperService);
        this.requestService = Objects.requireNonNull(requestService);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyCityOffersRepository = Objects.requireNonNull(technologyCityOffersRepository);
        this.technologyCountryOffersRepository = Objects.requireNonNull(technologyCountryOffersRepository);
    }

    public List<CountryDto> scrapTechnologyStatisticsForCountries(String countryName) {

        //        this.modelMapper.addMappings(mapperService.countryMapping);
//        this.modelMapper.addConverter(mapperService.countryOffersPer100kConverter);

        String selectedCountryUTF8 = countryName.toLowerCase();
        List<TechnologyCountryOffers> countriesOffers = new ArrayList<>();
        List<Technology> technologies = technologyRepository.findAll();
        Country country = countryRepository.findCountryByName(selectedCountryUTF8);
        UrlBuilder urlBuilder = new UrlBuilder();

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();

            String linkedinUrl = urlBuilder.linkedinBuildUrlForCityAndCountry(selectedTechnology, selectedCountryUTF8);
            String indeedUrl = urlBuilder.indeedBuildUrlForCountry(selectedTechnology, country.getCode());

            TechnologyCountryOffers technologyCountryOffers = new TechnologyCountryOffers(country, technology, LocalDate.now());

            try {
                technologyCountryOffers.setIndeed(requestService.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            technologyCountryOffers.setLinkedin(requestService.scrapLinkedinOffers(linkedinUrl));

            countriesOffers.add(technologyCountryOffers);
        });

        return countriesOffers
                .stream()
                .map(countryOffer -> modelMapper.map(technologyCountryOffersRepository.save(countryOffer), CountryDto.class))
                .collect(Collectors.toList());
    }

    public List<CityDto> scrapTechnologyStatisticsForCities(String cityName) {

        this.modelMapper.addMappings(mapperService.cityOffersMapper);
        this.modelMapper.addConverter(mapperService.cityOffersTotalConverter);

        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestService.removePolishSigns(cityNameUTF8).toLowerCase();
        List<Technology> technologies = technologyRepository.findAll();
        City city = cityRepository.findCityByName(cityNameUTF8);
        List<TechnologyCityOffers> technologyCityOffers = new ArrayList<>();
        UrlBuilder urlBuilder = new UrlBuilder();

        technologies.forEach(technology -> {

            String technologyName = technology.getName().toLowerCase();

            String linkedinDynamicURL = urlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedDynamicURL = urlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujDynamicURL = urlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsDynamicURL = urlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);

            TechnologyCityOffers offer = new TechnologyCityOffers(city, technology, LocalDate.now());

            try {
                offer.setIndeed(requestService.scrapIndeedOffers(indeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestService.scrapLinkedinOffers(linkedinDynamicURL));
            offer.setPracuj(requestService.scrapPracujOffers(pracujDynamicURL));
            offer.setNoFluffJobs(requestService.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));
            offer.setJustJoinIT(requestService.scrapJustJoin(city, technology));

            technologyCityOffers.add(offer);
        });

        return technologyCityOffers
                .stream()
                .map(cityOffer -> modelMapper.map(technologyCityOffersRepository.save(cityOffer), CityDto.class))
                .collect(Collectors.toList());
    }

}
