package com.Backend.service.scrap;

import com.Backend.dto.CountryDto;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyCountryOffers;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyCountryOffersRepository;
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
public class ScrapTechnologyCountry {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyCountryOffersRepository technologyCountryOffersRepository;
    private CountryRepository countryRepository;

    public ScrapTechnologyCountry(ModelMapper modelMapper, RequestCreator requestCreator, TechnologyRepository technologyRepository,
                                  CountryRepository countryRepository, TechnologyCountryOffersRepository technologyCountryOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(DtoMapper.technologyCountryOffersMapper);
        this.requestCreator = Objects.requireNonNull(requestCreator);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyCountryOffersRepository = Objects.requireNonNull(technologyCountryOffersRepository);
    }

    public List<CountryDto> scrapCountriesStatisticsForTechnology(String technologyName) {

        List<Country> countries = countryRepository.findAllCountriesWithCode();
        Technology technology = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyCountryOffers> technologyCountryOffers = new ArrayList<>();

        countries.forEach(country -> {

            String countryNameUTF8 = country.getName().toLowerCase();

            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);
            String indeedUrl = UrlBuilder.indeedBuildUrlForCountry(technologyName, country.getCode());

            TechnologyCountryOffers offer = new TechnologyCountryOffers(country, technology, LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));

            technologyCountryOffers.add(offer);
        });

        return technologyCountryOffers
                .stream()
                .map(countryOffer -> modelMapper.map(technologyCountryOffersRepository.save(countryOffer), CountryDto.class))
                .peek(countryDto -> countryDto.setPer100k(Math.round(countryDto.getIndeed() * 1.0 / (countryDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }
}
