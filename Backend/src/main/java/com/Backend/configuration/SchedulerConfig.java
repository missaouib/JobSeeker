package com.Backend.configuration;

import com.Backend.repository.CityRepository;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.service.CategoryService;
import com.Backend.service.old.CityService;
import com.Backend.service.old.CountryService;
import com.Backend.service.old.TechnologyService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class SchedulerConfig {

    private CityService cityService;
    private CountryService countryService;
    private TechnologyService technologyService;
    private CategoryService categoryService;
    private CityRepository cityRepository;
    private TechnologyRepository technologyRepository;
    private CountryRepository countryRepository;
    private List<String> cities;
    private List<String> technologies;
    private List<String> countries;

    public SchedulerConfig(CityService cityService, CountryService countryService, TechnologyService technologyService, CategoryService categoryService,
                           CityRepository cityRepository, TechnologyRepository technologyRepository, CountryRepository countryRepository) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.technologyService = technologyService;
        this.categoryService = categoryService;
        this.cityRepository = cityRepository;
        this.technologyRepository = technologyRepository;
        this.countryRepository = countryRepository;
    }

    @PostConstruct
    public void init(){
        this.cities = cityRepository.findAllNames();
        this.technologies = technologyRepository.findAllNames();
        this.countries = countryRepository.findAllNames();
    }

    @Scheduled(cron = "0 1 * * * *")
    public void sendRequests() {



        cities.forEach(city -> {
            technologyService.scrapTechnologyStatistics(city);
            categoryService.scrapCategoryStatistics(city);
        });

        technologies.forEach(technology -> {
            //scrap city and country, elo mejbi categories
            cityService.scrapItJobOffersInPoland(technology);
            countryService.scrapItJobOffersInWorld(technology);
        });

        countries.forEach(country -> {
            technologyService.scrapTechnologyStatistics(country);
        });

    }
}
