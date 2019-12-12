package com.Backend.service.scrap;

import com.Backend.domain.JustJoinIT;
import com.Backend.dto.CityDto;
import com.Backend.entity.City;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyCityOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyCityOffersRepository;
import com.Backend.service.DtoMapper;
import com.Backend.service.RequestCreator;
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
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyCityOffersRepository technologyCityOffersRepository;
    private CityRepository cityRepository;

    public ScrapTechnologyCity(ModelMapper modelMapper, RequestCreator requestCreator, TechnologyRepository technologyRepository,
                               CityRepository cityRepository, TechnologyCityOffersRepository technologyCityOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(DtoMapper.technologyCityOffersMapper);
        this.requestCreator = Objects.requireNonNull(requestCreator);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyCityOffersRepository = Objects.requireNonNull(technologyCityOffersRepository);
    }

    public List<CityDto> scrapCitiesStatisticsForTechnology(String technologyName) {

        List<City> cities = cityRepository.findAll();
        List<JustJoinIT> justJoinItOffers =  requestCreator.scrapJustJoinIT();
        Technology technology = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyCityOffers> technologyCityOffers = new ArrayList<>();

        cities.forEach(city -> {

            String cityNameUTF8 = city.getName().toLowerCase();
            String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);

            String linkedinDynamicURL = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedDynamicURL = UrlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujDynamicURL = UrlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsDynamicURL = UrlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);

            TechnologyCityOffers offer = new TechnologyCityOffers(city, technology, LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinDynamicURL));
            offer.setPracuj(requestCreator.scrapPracujOffers(pracujDynamicURL));
            offer.setNoFluffJobs(requestCreator.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));
            offer.setJustJoinIT(requestCreator.extractJustJoinItJson(justJoinItOffers, city, technology));

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
