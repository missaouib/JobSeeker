package com.Backend.service.scrap;

import com.Backend.dto.CityDto;
import com.Backend.entity.City;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyCityOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyCityOffersRepository;
import com.Backend.service.MapperService;
import com.Backend.service.RequestService;
import com.Backend.service.UrlBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScrapTechnologyCity {

    private ModelMapper modelMapper;
    private MapperService mapperService;
    private RequestService requestService;
    private TechnologyRepository technologyRepository;
    private TechnologyCityOffersRepository technologyCityOffersRepository;
    private CityRepository cityRepository;

    public ScrapTechnologyCity(ModelMapper modelMapper, RequestService requestService, TechnologyRepository technologyRepository,
                               CityRepository cityRepository, MapperService mapperService,
                               TechnologyCityOffersRepository technologyCityOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.mapperService = Objects.requireNonNull(mapperService);
        this.modelMapper.addMappings(mapperService.technologyCityOffersMapper);

        this.requestService = Objects.requireNonNull(requestService);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyCityOffersRepository = Objects.requireNonNull(technologyCityOffersRepository);
    }

    public List<CityDto> scrapCitiesStatisticsForTechnology(String technologyName) {

        List<City> cities = cityRepository.findAll();
        //List<JustJoinIT> justJoinItOffers =  requestService.scrapJustJoin();
        Technology technology = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyCityOffers> technologyCityOffers = new ArrayList<>();
        UrlBuilder urlBuilder = new UrlBuilder();

        cities.forEach(city -> {

            String cityNameUTF8 = city.getName().toLowerCase();
            String cityNameASCII = requestService.removePolishSigns(cityNameUTF8);

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
                .peek(cityDto -> cityDto.setTotal(cityDto.getLinkedin() + cityDto.getPracuj() + cityDto.getNoFluffJobs() + cityDto.getJustJoinIT()))
                .peek(cityDto -> cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

}
