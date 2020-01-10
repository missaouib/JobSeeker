package com.Backend.core.domain;

import com.Backend.core.service.DtoMapper;
import com.Backend.core.service.RequestCreator;
import com.Backend.core.service.UrlBuilder;
import com.Backend.infrastructure.dto.JobsOffersInWorldDto;
import com.Backend.infrastructure.entity.Country;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInWorld;
import com.Backend.infrastructure.repository.CountryRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
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

    List<JobsOffersInWorldDto> getItJobOffersInWorld(String technologyName) {
        List<TechnologyOffersInWorld> offers = technologyOffersInWorldRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName)
                .orElse(null));

        if(technologyName.equals("all technologies")) {
            return null;
        }
        else if (offers.isEmpty()) {
            return mapToDto(scrapItJobOffersInWorld(technologyName));
        } else {
            return mapToDto(offers);
        }
    }

    private <T> List<JobsOffersInWorldDto> mapToDto(final List<T> offers) {
        return offers
                .stream()
                .map(offersInWorld -> modelMapper.map(offersInWorld, JobsOffersInWorldDto.class))
                .peek(jobsOffersInWorldDto -> jobsOffersInWorldDto.setTotal(jobsOffersInWorldDto.getLinkedin() + jobsOffersInWorldDto.getIndeed()))
                .peek(jobsOffersInWorldDto -> jobsOffersInWorldDto.setPer100k(Math.round(jobsOffersInWorldDto.getTotal() * 1.0 / (jobsOffersInWorldDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

    private List<TechnologyOffersInWorld> scrapItJobOffersInWorld(String technologyName) {

        List<Country> countries = countryRepository.findAll();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyOffersInWorld> technologyOfferInWorlds = new ArrayList<>();

        countries.forEach(country -> {

            String countryNameUTF8 = country.getName().toLowerCase();
            TechnologyOffersInWorld offer = new TechnologyOffersInWorld(country, technologyOptional.orElse(null), LocalDate.now());

            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);

            if (country.getCode() != null) {

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

        return new ArrayList<>(technologyOptional
                .filter(ignoredTechnologyCountry -> !technologyOffersInWorldRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnologyCountry))
                .map(ignoredCity -> technologyOfferInWorlds
                        .stream()
                        .map(technologyCountry -> technologyOffersInWorldRepository.save(technologyCountry))
                        .collect(Collectors.toList()))
                .orElse(technologyOfferInWorlds));
    }
}
