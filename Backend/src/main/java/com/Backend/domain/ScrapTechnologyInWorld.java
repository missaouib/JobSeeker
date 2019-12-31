package com.Backend.domain;

import com.Backend.infrastructure.dto.JobsOffersInWorldDto;
import com.Backend.infrastructure.entity.Country;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInWorld;
import com.Backend.infrastructure.repository.CountryRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
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
class ScrapTechnologyInWorld {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersInWorldRepository technologyOffersInWorldRepository;
    private CountryRepository countryRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyCountryOffersMapper);
    }

    List<JobsOffersInWorldDto> loadItJobOffersInWorld(String technologyName) {
        List<TechnologyOffersInWorld> offers = technologyOffersInWorldRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName).orElse(null));

        if (offers.isEmpty()) {
            return scrapItJobOffersInWorld(technologyName);
        } else {
            return offers
                    .stream()
                    .map(country -> modelMapper.map(country, JobsOffersInWorldDto.class))
                    .collect(Collectors.toList());
        }
    }

    private List<JobsOffersInWorldDto> scrapItJobOffersInWorld(String technologyName) {

        List<Country> countries = countryRepository.findAll();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyOffersInWorld> technologyOfferInWorlds = new ArrayList<>();

        countries.forEach(country -> {

            String countryNameUTF8 = country.getName().toLowerCase();
            TechnologyOffersInWorld offer = new TechnologyOffersInWorld(country, technologyOptional.orElse(null), LocalDate.now());

            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);

            if(country.getCode() != null) {

                String indeedUrl = UrlBuilder.indeedBuildUrlForCountry(technologyName, country.getCode());

                try {
                    offer.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));

            technologyOfferInWorlds.add(offer);
        });

        return technologyOptional
                .filter(ignoredTechnology -> !technologyOffersInWorldRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnology))
                .map(ignoredCity -> technologyOfferInWorlds.stream().map(category -> modelMapper.map(technologyOffersInWorldRepository.save(category), JobsOffersInWorldDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> technologyOfferInWorlds.stream().map(city -> modelMapper.map(city, JobsOffersInWorldDto.class)).collect(Collectors.toList()))
                .stream()
                .peek(jobsOffersInWorldDto -> jobsOffersInWorldDto.setPer100k(Math.round(jobsOffersInWorldDto.getIndeed() * 1.0 / (jobsOffersInWorldDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }
}
