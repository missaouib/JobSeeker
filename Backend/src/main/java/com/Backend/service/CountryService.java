package com.Backend.service;

import com.Backend.dto.CountryDto;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.CountryOffers;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.CountryOffersRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private ModelMapper modelMapper;
    private ScrapService scrapService;
    private CountryRepository countryRepository;
    private CountryOffersRepository countryOffersRepository;
    private TechnologyRepository technologyRepository;

    public CountryService(ModelMapper modelMapper, ScrapService scrapService, CountryRepository countryRepository,
                          CountryOffersRepository countryOffersRepository, TechnologyRepository technologyRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(countryMapping);
        this.modelMapper.addConverter(per100kConverter);
        this.scrapService = Objects.requireNonNull(scrapService);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.countryOffersRepository = Objects.requireNonNull(countryOffersRepository);
        this.technologyRepository = technologyRepository;
    }

    private PropertyMap<CountryOffers, CountryDto> countryMapping = new PropertyMap<CountryOffers, CountryDto>() {
        protected void configure() {
            map().setName(source.getCountry().getName());
            map().setPopulation(source.getCountry().getPopulation());
            map().setArea(source.getCountry().getArea());
            map().setDensity(source.getCountry().getDensity());
            map().setId(source.getCountry().getId());
            using(per100kConverter).map(map().getPer100k());
        }
    };

    private Converter<Double, Double> per100kConverter = context -> {
        CountryOffers country = (CountryOffers) context.getParent().getSource();
        return (Math.round((country.getLinkedin() + country.getIndeed()) * 1.0 / (country.getCountry().getPopulation() * 1.0 / 100000) * 100.0) / 100.0);
    };

    public List<CountryDto> getItJobOffersInWorld(String technology) {

        List<CountryOffers> list = countryOffersRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technology).orElse(null));

        if(list.isEmpty()){
            return scrapItJobOffersInWorld(technology);
        } else {
            return list.stream().map(country -> modelMapper.map(country, CountryDto.class)).collect(Collectors.toList());
        }
    }

    public List<CountryDto> scrapItJobOffersInWorld(String technology) {
        String selectedTechnology = technology.toLowerCase();
        List<Country> countries = countryRepository.findAll();
        List<Country> countries2 = countryRepository.findAllCountriesWithCode();
        List<CountryOffers> countriesOffers = new ArrayList<>();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(selectedTechnology);

        countries2.forEach(country -> {
            //przekazuj to jako argument
            String selectedCountry = country.getName().toLowerCase();
            //to indeed pass CODE AS ARGUMENT

            String linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCountry;
            String IndeedDynamicURL = "https://" + country.getCode() + ".indeed.com/" + selectedTechnology + "-jobs";

            switch(country.getCode()) {
                case "us":
                    IndeedDynamicURL = "https://indeed.com/" + selectedTechnology + "-jobs";
                    break;
                case "my":
                    IndeedDynamicURL = "https://indeed.com.my/" + selectedTechnology + "-jobs";
            }

            switch(selectedTechnology){
                case "all jobs":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=&location=" + selectedCountry;
                    break;
                case "all it jobs":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/search?location=" + selectedCountry + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96";
                    break;
                case "c++":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/c++-jobs-" + selectedCountry;
                    break;
                case "c#":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCountry;
                    break;
            }

            CountryOffers countryOffers = new CountryOffers(country, technologyOptional.orElse(null), LocalDate.now());
            countryOffers.setLinkedin(scrapService.scrapLinkedinOffers(linkedinDynamicURL));
            try {
                countryOffers.setIndeed(scrapService.scrapIndeedOffers(IndeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }

            countriesOffers.add(countryOffers);
        });

        return technologyOptional
                .filter(ignoredTechnology -> !countryOffersRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnology))
                .map(ignoredCity -> countriesOffers.stream().map(category -> modelMapper.map(countryOffersRepository.save(category), CountryDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> countriesOffers.stream().map(city -> modelMapper.map(city, CountryDto.class)).collect(Collectors.toList()));
    }

}
