package com.Backend.configuration;

import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.infrastructure.repository.CountryRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
import com.Backend.domain.ScrapFacade;
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
    private CountryRepository countryRepository;
    private List<String> cities;
    private List<String> technologies;
    private List<String> countries;

    public SchedulerConfig(ScrapFacade scrapFacade, CityRepository cityRepository,
                           TechnologyRepository technologyRepository, CountryRepository countryRepository) {
        this.scrapFacade = Objects.requireNonNull(scrapFacade);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.countryRepository = Objects.requireNonNull(countryRepository);
    }

    @PostConstruct
    public void initLists() {
        this.cities = cityRepository.findAllNames();
        this.technologies = technologyRepository.findAllNames();
        this.countries = countryRepository.findAllNames();
    }

    @Scheduled(cron = "0 1 * * * *")
    public void cyclicScraping() {

        technologies.forEach(technology -> {
            scrapFacade.ItJobsOffersInPoland(technology);
            WaitRandomUnderSecond();
        });

        technologies.forEach(technology -> {
            scrapFacade.itJobOffersInWorld(technology);
            WaitRandomUnderSecond();
        });

        cities.forEach(city -> {
            scrapFacade.categoryStatisticsInPoland(city);
            WaitRandomUnderSecond();
        });

        countries.forEach(country -> {
            scrapFacade.categoryStatisticsInWorld(country);
            WaitRandomUnderSecond();
        });

    }

    private void WaitRandomUnderSecond(){
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
