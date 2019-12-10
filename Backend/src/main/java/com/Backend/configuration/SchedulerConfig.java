package com.Backend.configuration;

import com.Backend.repository.CityRepository;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.service.ScrapCategoryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class SchedulerConfig {

    private ScrapCategoryService scrapCategoryService;
    private CityRepository cityRepository;
    private TechnologyRepository technologyRepository;
    private CountryRepository countryRepository;
    private List<String> cities;
    private List<String> technologies;
    private List<String> countries;

    public SchedulerConfig(ScrapCategoryService scrapCategoryService,
                           CityRepository cityRepository, TechnologyRepository technologyRepository, CountryRepository countryRepository) {
        this.scrapCategoryService = scrapCategoryService;
        this.cityRepository = cityRepository;
        this.technologyRepository = technologyRepository;
        this.countryRepository = countryRepository;
    }

    @PostConstruct
    public void init() {
        this.cities = cityRepository.findAllNames();
        this.technologies = technologyRepository.findAllNames();
        this.countries = countryRepository.findAllNames();
    }

    @Scheduled(cron = "0 1 * * * *")
    public void sendRequests() {

//        cities.forEach(city -> {
//            technologyService.scrapTechnologyStatistics(city);
//            categoryService.scrapCategoryStatistics(city);
//        });
//
//        countries.forEach(country -> {
//            technologyService.scrapTechnologyStatistics(country);
//            categoryService.scrapCategoryStatistics(city);
//        });

    }
}
