package com.Backend.core.domain;

import com.Backend.infrastructure.dto.*;
import com.Backend.infrastructure.model.JustJoinIt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScrapFacade {

    private ScrapCategoryInPoland scrapCategoryInPoland;
    private ScrapTechnologyInPoland scrapTechnologyInPoland;
    private ScrapTechnologyInWorld scrapTechnologyInWorld;
    private TemporaryScrapTechnologyInPoland temporaryScrapTechnologyInPoland;
    private TemporaryScrapTechnologyInWorld temporaryScrapTechnologyInWorld;

    public List<JobsOffersInPolandDto> ItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {
        return scrapTechnologyInPoland.getItJobsOffersInPoland(technologyName.toLowerCase(), justJoinItOffers);
    }

    public List<JobsOffersInWorldDto> itJobOffersInWorld(String technologyName) {
        return scrapTechnologyInWorld.getItJobOffersInWorld(technologyName.toLowerCase());
    }

    public List<CategoryStatisticsInPolandDto> categoryStatisticsInPoland(String location) {
        location = addPolishSigns(location.toLowerCase());
        return scrapCategoryInPoland.getCategoryStatisticsInPoland(location);
    }

    public List<TechnologyStatisticsInPolandDto> technologyStatisticsInPoland(String location) {
        location = addPolishSigns(location.toLowerCase());
        return temporaryScrapTechnologyInPoland.getTechnologyStatisticsInPoland(location);
    }

    public List<TechnologyStatisticsInWorldDto> technologyStatisticsInWorld(String location) {
        return temporaryScrapTechnologyInWorld.loadTechnologyStatisticsInWorld(location);
    }

    private String addPolishSigns(String location) {

        switch (location) {
            case "krakow":
                location = "kraków";
                break;
            case "lodz":
                location = "łódź";
                break;
            case "wroclaw":
                location = "wrocław";
                break;
            case "poznan":
                location = "poznań";
                break;
            case "gdansk":
                location = "gdańsk";
                break;
            case "bialystok":
                location = "białystok";
                break;
            case "rzeszow":
                location = "rzeszów";
                break;
            case "torun":
                location = "toruń";
                break;
            case "gorzow wielkopolski":
                location = "gorzów wielkopolski";
                break;
            case "zielona gora":
                location = "zielona góra";
                break;
        }
        return location;
    }

}
