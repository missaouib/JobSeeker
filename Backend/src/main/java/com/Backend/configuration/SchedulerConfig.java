package com.Backend.configuration;

import com.Backend.core.domain.ScrapFacade;
import com.Backend.core.service.RequestCreator;
import com.Backend.core.service.UtilityClass;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Log4j2
@Configuration
public class SchedulerConfig {

    private static final Logger logger = LogManager.getLogger(SchedulerConfig.class);

    private ScrapFacade scrapFacade;
    private RequestCreator requestCreator;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private CategoryRepository categoryRepository;
    private TechnologyRepository technologyRepository;
    private CategoryOffersInPolandRepository categoryOffersInPolandRepository;
    private TechnologyOffersInPolandRepository technologyOffersInPolandRepository;
    private TechnologyOffersInWorldRepository technologyOffersInWorldRepository;
    private List<String> citiesNames;
    private List<String> technologiesNames;

    public SchedulerConfig(ScrapFacade scrapFacade, CityRepository cityRepository, TechnologyRepository technologyRepository,
                           CategoryOffersInPolandRepository categoryOffersInPolandRepository, TechnologyOffersInPolandRepository technologyOffersInPolandRepository,
                           TechnologyOffersInWorldRepository technologyOffersInWorldRepository, CategoryRepository categoryRepository,
                           CountryRepository countryRepository, RequestCreator requestCreator) {
        this.scrapFacade = Objects.requireNonNull(scrapFacade);
        this.requestCreator = Objects.requireNonNull(requestCreator);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.categoryOffersInPolandRepository = Objects.requireNonNull(categoryOffersInPolandRepository);
        this.technologyOffersInPolandRepository = Objects.requireNonNull(technologyOffersInPolandRepository);
        this.technologyOffersInWorldRepository = Objects.requireNonNull(technologyOffersInWorldRepository);
    }

    @PostConstruct
    public void initLists() {
        this.citiesNames = cityRepository.findAllNames();
        this.technologiesNames = technologyRepository.findAllNames();
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void cyclicScraping() {

        List<JustJoinIt> justJoinItOffers = requestCreator.scrapJustJoinIT();
        logger.info("1x " + LocalTime.now());

        runForCities(justJoinItOffers);
        logger.info("2x " + LocalTime.now());

        runForCountries();
        logger.info("3x " + LocalTime.now());

        runForCategories();
        logger.info("4x " + LocalTime.now());

        verifyData(justJoinItOffers);
    }

    private void runForCities(List<JustJoinIt> justJoinItOffers) {
        technologiesNames.forEach(technologyName -> {
            scrapFacade.ItJobsOffersInPoland(technologyName, justJoinItOffers);
            UtilityClass.waitRandomFromToSeconds(5000, 10000);
        });
    }

    private void runForCountries() {
        technologiesNames.forEach(technologyName -> {
            scrapFacade.itJobOffersInWorld(technologyName);
            UtilityClass.waitRandomFromToSeconds(10000, 20000);
        });
    }

    private void runForCategories() {
        citiesNames.forEach(cityName -> {
            scrapFacade.categoryStatisticsInPoland(cityName);
            UtilityClass.waitRandomFromToSeconds(5000, 10000);
        });
    }

    private void verifyData(List<JustJoinIt> justJoinItOffers) {

        if (categoryOffersInPolandRepository.findByDate(LocalDate.now()).size() != cityRepository.findAll().size() * categoryRepository.findAll().size()) {
            UtilityClass.waitRandomFromToSeconds(1200000, 1800000);
            runForCities(justJoinItOffers);
        }

        if (technologyOffersInPolandRepository.findByDate(LocalDate.now()).size() != cityRepository.findAll().size() * technologyRepository.findAll().size()) {
            UtilityClass.waitRandomFromToSeconds(1200000, 1800000);
            runForCountries();
        }

        if (technologyOffersInWorldRepository.findByDate(LocalDate.now()).size() != countryRepository.findAll().size() * technologyRepository.findAll().size()) {
            UtilityClass.waitRandomFromToSeconds(1200000, 1800000);
            runForCategories();
        }

    }

}
