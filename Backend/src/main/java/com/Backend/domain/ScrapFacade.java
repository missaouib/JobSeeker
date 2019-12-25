package com.Backend.domain;

import com.Backend.infrastructure.dto.CategoryDto;
import com.Backend.infrastructure.dto.CityDto;
import com.Backend.infrastructure.dto.CountryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScrapFacade {

    ScrapCategoryCity scrapCategoryCity;
    ScrapTechnologyCity scrapTechnologyCity;
    ScrapTechnologyCountry scrapTechnologyCountry;

    public List<CityDto> ItJobsOffersInPoland(String technologyName) {
        return scrapTechnologyCity.loadCitiesStatisticsForTechnology(technologyName.toLowerCase());
    }

    public List<CountryDto> itJobOffersInWorld(String technologyName) {
        return scrapTechnologyCountry.loadCountriesStatisticsForTechnology(technologyName.toLowerCase());
    }

    public List<CategoryDto> categoryStatisticsInPoland(String location) {
        return scrapCategoryCity.loadCategoryStatisticsInPoland(location.toLowerCase());
    }

}
