package com.Backend.configuration;

import com.Backend.domain.ScrapFacade;
import com.Backend.infrastructure.repository.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Configuration
public class SchedulerConfig {

    private ScrapFacade scrapFacade;
    private CityRepository cityRepository;
    private TechnologyRepository technologyRepository;
    private CategoryCityOffersRepository categoryCityOffersRepository;
    private TechnologyCityOffersRepository technologyCityOffersRepository;
    private TechnologyCountryOffersRepository technologyCountryOffersRepository;
    private List<String> cities;
    private List<String> technologies;

    public SchedulerConfig(ScrapFacade scrapFacade, CityRepository cityRepository, TechnologyRepository technologyRepository) {
        this.scrapFacade = Objects.requireNonNull(scrapFacade);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
    }

    @PostConstruct
    public void initLists() {
        this.cities = cityRepository.findAllNames();
        this.technologies = technologyRepository.findAllNames();
    }

    @Scheduled(cron = "0 45 14 * * *")
    public void cyclicScraping() {

        technologies.forEach(technologyName -> {
            scrapFacade.ItJobsOffersInPoland(technologyName);
            WaitRandomUnderTwoSeconds();
        });

        technologies.forEach(technologyName -> {
            scrapFacade.itJobOffersInWorld(technologyName);
            WaitRandomUnderTwoSeconds();
        });

        cities.forEach(cityName -> {
            scrapFacade.categoryStatisticsInPoland(cityName);
            WaitRandomUnderTwoSeconds();
        });

        verifyData();

    }

    private void verifyData() {

        //categoryCityOffersRepository.findByDate();

        //repository
        //if something
        //wait and scrap
    }

    private void WaitRandomUnderTwoSeconds() {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
