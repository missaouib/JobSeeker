package com.Backend.service.scrap;

import com.Backend.dto.CountryDto;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyCountryOffers;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyCountryOffersRepository;
import com.Backend.service.MapperService;
import com.Backend.service.RequestService;
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
public class ScrapCategoryCountry {

    private ModelMapper modelMapper;
    private MapperService mapperService;
    private RequestService requestService;
    private TechnologyRepository technologyRepository;
    private TechnologyCountryOffersRepository technologyCountryOffersRepository;
    private CountryRepository countryRepository;

    public ScrapCategoryCountry(ModelMapper modelMapper, RequestService requestService, TechnologyRepository technologyRepository,
                               CountryRepository countryRepository, MapperService mapperService,
                               TechnologyCountryOffersRepository technologyCountryOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.mapperService = Objects.requireNonNull(mapperService);
        this.modelMapper.addMappings(mapperService.technologyCountryOffersMapper);
        this.modelMapper.addConverter(mapperService.countryOffersPer100kConverter);

        this.requestService = Objects.requireNonNull(requestService);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyCountryOffersRepository = Objects.requireNonNull(technologyCountryOffersRepository);
    }

    public List<CountryDto> scrapTechnologyStatisticsForCountries(String countryName) {

        String selectedCountryUTF8 = countryName.toLowerCase();
        List<TechnologyCountryOffers> countriesOffers = new ArrayList<>();
        List<Technology> technologies = technologyRepository.findAll();
        Country country = countryRepository.findCountryByName(selectedCountryUTF8);
        UrlBuilder urlBuilder = new UrlBuilder();

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();

            String linkedinUrl = urlBuilder.linkedinBuildUrlForCityAndCountry(selectedTechnology, selectedCountryUTF8);
            String indeedUrl = urlBuilder.indeedBuildUrlForCountry(selectedTechnology, country.getCode());

            TechnologyCountryOffers technologyCountryOffers = new TechnologyCountryOffers(country, technology, LocalDate.now());

            try {
                technologyCountryOffers.setIndeed(requestService.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            technologyCountryOffers.setLinkedin(requestService.scrapLinkedinOffers(linkedinUrl));

            countriesOffers.add(technologyCountryOffers);
        });

        return countriesOffers
                .stream()
                .map(countryOffer -> modelMapper.map(technologyCountryOffersRepository.save(countryOffer), CountryDto.class))
                .collect(Collectors.toList());
    }
}
