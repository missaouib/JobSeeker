package com.Backend.service.implementation;

import com.Backend.dto.CountryDto;
import com.Backend.entity.Country;
import com.Backend.entity.offers.CountryOffers;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.offers.CountryOffersRepository;
import com.Backend.service.CountryService;
import com.Backend.service.ScrapJobService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CountryServiceImp implements CountryService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;
    private CountryRepository countryRepository;
    private CountryOffersRepository countryOffersRepository;

    public CountryServiceImp(ModelMapper modelMapper, ScrapJobService scrapJobService, CountryRepository countryRepository, CountryOffersRepository countryOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(countryMapping);
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.countryOffersRepository = Objects.requireNonNull(countryOffersRepository);
    }

    private PropertyMap<CountryOffers, CountryDto> countryMapping = new PropertyMap<>() {
        protected void configure() {
            map().setName(source.getCountry().getName());
            map().setPopulation(source.getCountry().getPopulation());
            map().setArea(source.getCountry().getArea());
            map().setDensity(source.getCountry().getDensity());
        }
    };

    public List<CountryDto> scrapItJobOffersInWorld(ModelMap technology) {
        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<Country> countries = countryRepository.findAll();
        List<CountryOffers> countriesOffers = new ArrayList<>();
        countries.forEach(country -> {

            String selectedCountry = country.getName().toLowerCase();

            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCountry);

            switch(selectedTechnology){
                case "all jobs":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=&location=" + selectedCountry);
                    break;
                case "all it jobs":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCountry + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                    break;
                case "c++":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCountry);
                    break;
                case "c#":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCountry);
                    break;
            }

            CountryOffers countryOffers = new CountryOffers(country, LocalDate.now());
            countryOffers.setLinkedin(scrapJobService.getLinkedinOffers(linkedinURL));
            countryOffers.setPer100k((double) Math.round((countryOffers.getLinkedin() * 1.0 / (country.getPopulation() * 1.0 / 100000)) * 100) / 100);
            countriesOffers.add(countryOffers);
        });

        return countriesOffers.stream().map(country -> modelMapper.map(countryOffersRepository.save(country), CountryDto.class)).collect(Collectors.toList());
    }
}
