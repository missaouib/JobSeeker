package com.Backend.domain;

import com.Backend.infrastructure.dto.JobsOffersInWorldDto;
import com.Backend.infrastructure.entity.Country;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyCountryOffers;
import com.Backend.infrastructure.repository.CountryRepository;
import com.Backend.infrastructure.repository.TechnologyCountryOffersRepository;
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
class ScrapTechnologyCountry {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyCountryOffersRepository technologyCountryOffersRepository;
    private CountryRepository countryRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyCountryOffersMapper);
    }

    List<JobsOffersInWorldDto> loadCountriesStatisticsForTechnology(String technologyName) {
        List<TechnologyCountryOffers> offers = technologyCountryOffersRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName).orElse(null));

        if (offers.isEmpty()) {
            return scrapCountriesStatisticsForTechnology(technologyName);
        } else {
            return offers
                    .stream()
                    .map(country -> modelMapper.map(country, JobsOffersInWorldDto.class))
                    .collect(Collectors.toList());
        }
    }

    private List<JobsOffersInWorldDto> scrapCountriesStatisticsForTechnology(String technologyName) {

        //List<Country> countries = countryRepository.findAllCountriesWithCode();
        List<Country> countries = countryRepository.findAll();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyCountryOffers> technologyCountryOffers = new ArrayList<>();

        countries.forEach(country -> {

            String countryNameUTF8 = country.getName().toLowerCase();
            TechnologyCountryOffers offer = new TechnologyCountryOffers(country, technologyOptional.orElse(null), LocalDate.now());

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

            technologyCountryOffers.add(offer);
        });

        return technologyOptional
                .filter(ignoredTechnology -> !technologyCountryOffersRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnology))
                .map(ignoredCity -> technologyCountryOffers.stream().map(category -> modelMapper.map(technologyCountryOffersRepository.save(category), JobsOffersInWorldDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> technologyCountryOffers.stream().map(city -> modelMapper.map(city, JobsOffersInWorldDto.class)).collect(Collectors.toList()))
                .stream()
                .peek(jobsOffersInWorldDto -> jobsOffersInWorldDto.setPer100k(Math.round(jobsOffersInWorldDto.getIndeed() * 1.0 / (jobsOffersInWorldDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }
}
