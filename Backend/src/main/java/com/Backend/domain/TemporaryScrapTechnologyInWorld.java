package com.Backend.domain;

import com.Backend.infrastructure.dto.TechnologyStatisticsInWorldDto;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.service.UrlBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TemporaryScrapTechnologyInWorld {

    public List<TechnologyStatisticsInWorldDto> loadTechnologyStatisticsInWorld(String countryName) {

    }

    public List<TechnologyStatisticsInWorldDto> scrapTechnologyStatisticsInWorld(String countryName) {

        String countryNameUTF8 = countryName.toLowerCase();
        List<TechnologyCountryOffers> countriesOffers = new ArrayList<>();
        List<Technology> technologies = technologyRepository.findAll();
        Country country = countryRepository.findCountryByName(countryNameUTF8);

        technologies.forEach(technology -> {

            String technologyName = technology.getName().toLowerCase();
            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);
            String indeedUrl = UrlBuilder.indeedBuildUrlForCountry(technologyName, country.getCode());

            TechnologyCountryOffers technologyCountryOffers = new TechnologyCountryOffers(country, technology, LocalDate.now());

            try {
                technologyCountryOffers.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            technologyCountryOffers.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));

            countriesOffers.add(technologyCountryOffers);
        });

        return countriesOffers
                .stream()
                .map(countryOffer -> modelMapper.map(technologyCountryOffersRepository.save(countryOffer), CountryDto.class))
                .collect(Collectors.toList());
    }

}
