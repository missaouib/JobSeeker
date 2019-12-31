package com.Backend.domain;

import com.Backend.infrastructure.dto.JobsOffersInPolandDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ScrapTechnologyInPoland {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersInPolandRepository technologyOffersInPolandRepository;
    private CityRepository cityRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyCityOffersMapper);
    }

    List<JobsOffersInPolandDto> loadItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {
        List<TechnologyOffersInPoland> offers = technologyOffersInPolandRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName).orElse(null));

        if (offers.isEmpty()) {
            return scrapItJobsOffersInPoland(technologyName, justJoinItOffers);
        } else {
            return offers.stream()
                    .map(cityOffer -> modelMapper.map(cityOffer, JobsOffersInPolandDto.class))
                    .collect(Collectors.toList());
        }
    }

    private List<JobsOffersInPolandDto> scrapItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {

        List<City> cities = cityRepository.findAll();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyOffersInPoland> technologyOfferInPolands = new ArrayList<>();

        if(justJoinItOffers.isEmpty()){
            justJoinItOffers = requestCreator.scrapJustJoinIT();
        }
        List<JustJoinIt> finalJustJoinItOffers = justJoinItOffers;

        cities.forEach(city -> {

            String cityNameUTF8 = city.getName().toLowerCase();
            String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);

            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedUrl = UrlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujUrl = UrlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsUrl = UrlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);

            TechnologyOffersInPoland offer = new TechnologyOffersInPoland(city, technologyOptional.orElse(null), LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));
            offer.setPracuj(requestCreator.scrapPracujOffers(pracujUrl));
            offer.setNoFluffJobs(requestCreator.scrapNoFluffJobsOffers(noFluffJobsUrl));
            offer.setJustJoinIt(requestCreator.extractJustJoinItJson(finalJustJoinItOffers, city.getName(), technologyName));

            technologyOfferInPolands.add(offer);
        });

        return technologyOptional
                .filter(ignoredTechnologyCity -> !technologyOffersInPolandRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnologyCity))
                .map(savedTechnologyCity -> technologyOfferInPolands
                        .stream()
                        .map(technologyCity -> modelMapper.map(technologyOffersInPolandRepository.save(technologyCity), JobsOffersInPolandDto.class))
                        .collect(Collectors.toList()))
                .orElseGet(() -> technologyOfferInPolands
                        .stream()
                        .map(technologyCity -> modelMapper.map(technologyCity, JobsOffersInPolandDto.class)).collect(Collectors.toList()))
                .stream()
                .peek(jobsOffersInPolandDto -> jobsOffersInPolandDto.setTotal(jobsOffersInPolandDto.getLinkedin() + jobsOffersInPolandDto.getPracuj() + jobsOffersInPolandDto.getNoFluffJobs() + jobsOffersInPolandDto.getJustJoinIT()))
                .peek(jobsOffersInPolandDto -> jobsOffersInPolandDto.setPer100k(Math.round(jobsOffersInPolandDto.getTotal() * 1.0 / (jobsOffersInPolandDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

}
