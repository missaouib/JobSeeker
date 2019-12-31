package com.Backend.configuration;

import com.Backend.domain.ScrapFacade;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.*;
import com.Backend.service.RequestCreator;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Configuration
public class SchedulerConfig {

    private ScrapFacade scrapFacade;
    private RequestCreator requestCreator;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private CategoryRepository categoryRepository;
    private TechnologyRepository technologyRepository;
    private CategoryCityOffersRepository categoryCityOffersRepository;
    private TechnologyCityOffersRepository technologyCityOffersRepository;
    private TechnologyCountryOffersRepository technologyCountryOffersRepository;
    private List<String> citiesNames;
    private List<String> technologiesNames;

    public SchedulerConfig(ScrapFacade scrapFacade, CityRepository cityRepository, TechnologyRepository technologyRepository,
    CategoryCityOffersRepository categoryCityOffersRepository, TechnologyCityOffersRepository technologyCityOffersRepository,
                           TechnologyCountryOffersRepository technologyCountryOffersRepository, CategoryRepository categoryRepository,
                           CountryRepository countryRepository, RequestCreator requestCreator) {
        this.scrapFacade = Objects.requireNonNull(scrapFacade);
        this.requestCreator = Objects.requireNonNull(requestCreator);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.categoryCityOffersRepository = Objects.requireNonNull(categoryCityOffersRepository);
        this.technologyCityOffersRepository = Objects.requireNonNull(technologyCityOffersRepository);
        this.technologyCountryOffersRepository = Objects.requireNonNull(technologyCountryOffersRepository);
    }

    @PostConstruct
    public void initLists() {
        this.citiesNames = cityRepository.findAllNames();
        this.technologiesNames = technologyRepository.findAllNames();
    }

    private void runForCities(List<JustJoinIt> justJoinItOffers){
        technologiesNames.forEach(technologyName -> {
            scrapFacade.ItJobsOffersInPoland(technologyName, justJoinItOffers);
            waitRandomUnderTwoSeconds();
        });
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void cyclicScraping() {

        List<JustJoinIt> justJoinItOffers = requestCreator.scrapJustJoinIT();

        runForCities(justJoinItOffers);
        runForCountries();
        runForCategories();

        verifyData(justJoinItOffers);
    }

    private void runForCountries(){
        technologiesNames.forEach(technologyName -> {
            scrapFacade.itJobOffersInWorld(technologyName);
            waitRandomUnderTwoSeconds();
        });
    }

    private void runForCategories(){
        citiesNames.forEach(cityName -> {
            scrapFacade.categoryStatisticsInPoland(cityName);
            waitRandomUnderTwoSeconds();
        });
    }

    private void verifyData(List<JustJoinIt> justJoinItOffers) {

        if(categoryCityOffersRepository.findByDate(LocalDate.now()).size() != cityRepository.findAll().size() * categoryRepository.findAll().size()) {
            waitRandomFrom20To30Minutes();
            runForCities(justJoinItOffers);
        }

        if(technologyCityOffersRepository.findByDate(LocalDate.now()).size() != cityRepository.findAll().size() * technologyRepository.findAll().size()){
            waitRandomFrom20To30Minutes();
            runForCountries();
        }

        if(technologyCountryOffersRepository.findByDate(LocalDate.now()).size() != countryRepository.findAll().size() * technologyRepository.findAll().size()){
            waitRandomFrom20To30Minutes();
            runForCategories();
        }

    }

    private void waitRandomUnderTwoSeconds() {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitRandomFrom20To30Minutes(){
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1200000, 1800000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
