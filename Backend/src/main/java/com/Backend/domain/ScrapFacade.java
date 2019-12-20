package com.Backend.domain;

import com.Backend.infrastructure.dto.CategoryDto;
import com.Backend.infrastructure.dto.CityDto;
import com.Backend.infrastructure.dto.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@AllArgsConstructor
public class ScrapFacade {

    ScrapCategoryCity scrapCategoryCity;
    ScrapCategoryCountry scrapCategoryCountry;
    ScrapTechnologyCity scrapTechnologyCity;
    ScrapTechnologyCountry scrapTechnologyCountry;

    public List<CityDto> ItJobsOffersInPoland(String technologyName) {
        return scrapTechnologyCity.loadCitiesStatisticsForTechnology(technologyName);
    }

    public List<CountryDto> itJobOffersInWorld(String technologyName) {
        return scrapTechnologyCountry.loadCountriesStatisticsForTechnology(technologyName);
    }

    public List<CategoryDto> categoryStatisticsInPoland(String location) {
        return scrapCategoryCity.loadCategoryStatisticsInPoland(location);
    }

    public List<CategoryDto> categoryStatisticsInWorld(String location) {
        return scrapCategoryCountry.scrapCategoryStatisticsForCountries(location);
    }

}
