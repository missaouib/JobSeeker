package com.Backend.core.domain;

import com.Backend.infrastructure.dto.TechnologyStatisticsInPolandDto;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
import com.Backend.core.service.DtoMapper;
import com.Backend.core.service.RequestCreator;
import com.Backend.core.service.UrlBuilder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TemporaryScrapTechnologyInPoland {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersInPolandRepository technologyOffersInPolandRepository;
    private CityRepository cityRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyStatisticsInPolandMapper);
    }

    List<TechnologyStatisticsInPolandDto> getTechnologyStatisticsInPoland(String cityName) {
        List<TechnologyOffersInPoland> offers = technologyOffersInPolandRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(cityName)
                .orElse(null));

        if (offers.size() < 42) {
            return mapToDto(scrapTechnologyStatisticsInWorld(cityName));
        } else {
            return mapToDto(offers);
        }
    }

    private <T> List<TechnologyStatisticsInPolandDto> mapToDto(final List<T> offers) {
        return offers.stream()
                .map(technologyStatisticsInPoland -> modelMapper.map(technologyStatisticsInPoland, TechnologyStatisticsInPolandDto.class))
                .peek(statsInPoland -> statsInPoland.setTotal(statsInPoland.getLinkedin() + statsInPoland.getIndeed() + statsInPoland.getPracuj() + statsInPoland.getNoFluffJobs() + statsInPoland.getJustJoinIt()))
                .collect(Collectors.toList());
    }

    private List<TechnologyOffersInPoland> scrapTechnologyStatisticsInWorld(String cityName) {

        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8).toLowerCase();
        List<Technology> technologies = technologyRepository.findAll();
        City city = cityRepository.findCityByName(cityNameUTF8).orElse(null);
        List<TechnologyOffersInPoland> technologyOfferInPoland = new ArrayList<>();
        List<JustJoinIt> justJoinItOffers = requestCreator.scrapJustJoinIT();

        technologies.forEach(technology -> {

            String technologyName = technology.getName().toLowerCase();

            String linkedinDynamicURL = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedDynamicURL = UrlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujDynamicURL = UrlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsDynamicURL = UrlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);

            TechnologyOffersInPoland offer = new TechnologyOffersInPoland(city, technology, LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinDynamicURL));
            offer.setPracuj(requestCreator.scrapPracujOffers(pracujDynamicURL));
            offer.setNoFluffJobs(requestCreator.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));
            offer.setJustJoinIt(requestCreator.extractJustJoinItJson(justJoinItOffers, cityNameUTF8, technologyName));

            technologyOfferInPoland.add(offer);
        });

        return technologyOfferInPoland;
    }

}


