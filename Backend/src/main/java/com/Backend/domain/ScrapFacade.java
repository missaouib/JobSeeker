package com.Backend.domain;

import com.Backend.infrastructure.dto.CategoryStatisticsInPolandDto;
import com.Backend.infrastructure.dto.JobsOffersInPolandDto;
import com.Backend.infrastructure.dto.JobsOffersInWorldDto;
import com.Backend.infrastructure.model.JustJoinIt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScrapFacade {

    private ScrapCategoryCity scrapCategoryCity;
    private ScrapTechnologyCity scrapTechnologyCity;
    private ScrapTechnologyCountry scrapTechnologyCountry;

    public List<JobsOffersInPolandDto> ItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {
        return scrapTechnologyCity.loadCitiesStatisticsForTechnology(technologyName.toLowerCase(), justJoinItOffers);
    }

    public List<JobsOffersInWorldDto> itJobOffersInWorld(String technologyName) {
        return scrapTechnologyCountry.loadCountriesStatisticsForTechnology(technologyName.toLowerCase());
    }

    public List<CategoryStatisticsInPolandDto> categoryStatisticsInPoland(String location) {
        return scrapCategoryCity.loadCategoryStatisticsInPoland(location.toLowerCase());
    }

}
