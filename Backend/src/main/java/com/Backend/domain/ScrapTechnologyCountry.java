package com.Backend.domain;

import com.Backend.infrastructure.dto.CountryDto;
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

    List<CountryDto> loadCountriesStatisticsForTechnology(String technologyName) {
        List<TechnologyCountryOffers> listOffers = technologyCountryOffersRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName).orElse(null));

        if (listOffers.isEmpty()) {
            return scrapCountriesStatisticsForTechnology(technologyName);
        } else {
            return listOffers.stream().map(country -> modelMapper.map(country, CountryDto.class)).collect(Collectors.toList());
        }
    }

    private List<CountryDto> scrapCountriesStatisticsForTechnology(String technologyName) {

        List<Country> countries = countryRepository.findAllCountriesWithCode();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyCountryOffers> technologyCountryOffers = new ArrayList<>();

        countries.forEach(country -> {

            String countryNameUTF8 = country.getName().toLowerCase();

            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);
            String indeedUrl = UrlBuilder.indeedBuildUrlForCountry(technologyName, country.getCode());

            TechnologyCountryOffers offer = new TechnologyCountryOffers(country, technologyOptional.orElse(null), LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));

            technologyCountryOffers.add(offer);
        });

        return technologyOptional
                .filter(ignoredTechnology -> !technologyCountryOffersRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnology))
                .map(ignoredCity -> technologyCountryOffers.stream().map(category -> modelMapper.map(technologyCountryOffersRepository.save(category), CountryDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> technologyCountryOffers.stream().map(city -> modelMapper.map(city, CountryDto.class)).collect(Collectors.toList()))
                .stream()
                .peek(countryDto -> countryDto.setPer100k(Math.round(countryDto.getIndeed() * 1.0 / (countryDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }
}
