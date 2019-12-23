package com.Backend.domain;

import com.Backend.infrastructure.dto.CityDto;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyCityOffers;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.infrastructure.repository.TechnologyCityOffersRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
import com.Backend.service.DtoMapper;
import com.Backend.service.RequestCreator;
import com.Backend.service.UrlBuilder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ScrapTechnologyCity {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyCityOffersRepository technologyCityOffersRepository;
    private CityRepository cityRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyCityOffersMapper);
    }

    List<CityDto> loadCitiesStatisticsForTechnology(String technologyName) {
        List<TechnologyCityOffers> listOffers = technologyCityOffersRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName).orElse(null));

        if (listOffers.isEmpty()) {
            return scrapCitiesStatisticsForTechnology(technologyName);
        } else {
            return listOffers.stream().map(city -> modelMapper.map(city, CityDto.class)).collect(Collectors.toList());
        }
    }

    private List<CityDto> scrapCitiesStatisticsForTechnology(String technologyName) {

        List<City> cities = cityRepository.findAll();
        List<JustJoinIt> justJoinItOffers = requestCreator.scrapJustJoinIT();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyCityOffers> technologyCityOffers = new ArrayList<>();

        cities.forEach(city -> {

            String cityNameUTF8 = city.getName().toLowerCase();
            String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);

            String linkedinDynamicURL = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedDynamicURL = UrlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujDynamicURL = UrlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsDynamicURL = UrlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);

            TechnologyCityOffers offer = new TechnologyCityOffers(city, technologyOptional.orElse(null), LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinDynamicURL));
            offer.setPracuj(requestCreator.scrapPracujOffers(pracujDynamicURL));
            offer.setNoFluffJobs(requestCreator.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));
            offer.setJustJoinIT(requestCreator.extractJustJoinItJson(justJoinItOffers, city, technologyOptional.orElse(null), technologyName));

            technologyCityOffers.add(offer);
        });

        return technologyOptional
                .filter(ignoredTechnologyCity -> !technologyCityOffersRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnologyCity))
                .map(savedTechnologyCity -> technologyCityOffers
                        .stream()
                        .map(technologyCity -> modelMapper.map(technologyCityOffersRepository.save(technologyCity), CityDto.class))
                        .collect(Collectors.toList()))
                .orElseGet(() -> technologyCityOffers
                        .stream()
                        .map(technologyCity -> modelMapper.map(technologyCity, CityDto.class)).collect(Collectors.toList()))
                .stream()
                .peek(cityDto -> cityDto.setTotal(cityDto.getLinkedin() + cityDto.getPracuj() + cityDto.getNoFluffJobs() + cityDto.getJustJoinIT()))
                .peek(cityDto -> cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

}
