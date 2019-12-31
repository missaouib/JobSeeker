package com.Backend.domain;

import com.Backend.infrastructure.dto.CityDto;
import com.Backend.infrastructure.dto.JobsOffersInPolandDto;
import com.Backend.infrastructure.dto.TechnologyStatisticsInPolandDto;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TemporaryScrapTechnologyInPoland {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersInPolandRepository technologyOffersInPolandRepository;
    private CityRepository cityRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.);
    }

    public List<TechnologyStatisticsInPolandDto> loadTechnologyStatisticsInPoland(String cityName) {

    }

    public List<TechnologyStatisticsInPolandDto> scrapTechnologyStatisticsInWorld(String cityName) {

        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8).toLowerCase();
        List<Technology> technologies = technologyRepository.findAll();
        City city = cityRepository.findCityByName(cityNameUTF8).orElse(null);
        List<TechnologyOffersInPoland> technologyOfferInPolands = new ArrayList<>();
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
            offer.setJustJoinIt(requestCreator.extractJustJoinItJson(justJoinItOffers, city.getName(), technologyName));

            technologyOfferInPolands.add(offer);
        });

        return technologyOfferInPolands
                .stream()
                .map(cityOffer -> modelMapper.map(technologyOffersInPolandRepository.save(cityOffer), TechnologyStatisticsInPolandDto.class))
                .peek(cityDto -> cityDto.setTotal(cityDto.getLinkedin() + cityDto.getPracuj() + cityDto.getNoFluffJobs() + cityDto.getJustJoinIT()))
                .peek(cityDto -> cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

}


