package com.Backend.configuration;

import com.Backend.repository.CityRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.service.CategoryService;
import com.Backend.service.CityService;
import com.Backend.service.CountryService;
import com.Backend.service.TechnologyService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class RequestCreater {

    private CityService cityService;
    private CountryService countryService;
    private TechnologyService technologyService;
    private CategoryService categoryService;
    private CityRepository cityRepository;
    private TechnologyRepository technologyRepository;
    private List<String> cities;
    private List<String> technologies;

    public RequestCreater(CityService cityService, CountryService countryService, TechnologyService technologyService, CategoryService categoryService,
                          CityRepository cityRepository, TechnologyRepository technologyRepository) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.technologyService = technologyService;
        this.categoryService = categoryService;
        this.cityRepository = cityRepository;
        this.technologyRepository = technologyRepository;
    }

    @PostConstruct
    public void init(){
        this.cities = cityRepository.findAllNames();
        this.technologies = technologyRepository.findAllNames();
    }

    @Scheduled(cron = "0 1 * * * *")
    public void sendRequests() {

        cities.forEach(city -> {
            technologyService.scrapTechnologyStatistics(city);
            categoryService.scrapCategoryStatistics(city);
        });

        technologies.forEach(technology -> {
            cityService.scrapItJobOffersInPoland(technology);
            countryService.scrapItJobOffersInWorld(technology);
        });
    }
}
