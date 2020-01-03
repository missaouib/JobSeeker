package com.Backend.domain;

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
        return scrapCategoryInPoland.getCategoryStatisticsInPoland(location.toLowerCase());
    }

    public List<TechnologyStatisticsInPolandDto> technologyStatisticsInPoland(String location) {
        return temporaryScrapTechnologyInPoland.getTechnologyStatisticsInPoland(location);
    }

    public List<TechnologyStatisticsInWorldDto> technologyStatisticsInWorld(String location) {
        return temporaryScrapTechnologyInWorld.loadTechnologyStatisticsInWorld(location);
    }

}
