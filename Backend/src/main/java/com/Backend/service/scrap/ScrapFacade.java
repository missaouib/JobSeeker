package com.Backend.service.scrap;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CityDto;
import com.Backend.dto.CountryDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ScrapFacade {

    ScrapCategoryCity scrapCategoryCity;
    ScrapCategoryCountry scrapCategoryCountry;
    ScrapTechnologyCity scrapTechnologyCity;
    ScrapTechnologyCountry scrapTechnologyCountry;

    public ScrapFacade(ScrapCategoryCity scrapCategoryCity, ScrapCategoryCountry scrapCategoryCountry,
                       ScrapTechnologyCity scrapTechnologyCity, ScrapTechnologyCountry scrapTechnologyCountry) {
        this.scrapCategoryCity = scrapCategoryCity;
        this.scrapCategoryCountry = scrapCategoryCountry;
        this.scrapTechnologyCity = scrapTechnologyCity;
        this.scrapTechnologyCountry = scrapTechnologyCountry;
    }

    public List<CityDto> ItJobsOffersInPoland(String technologyName) {
        return scrapTechnologyCity.scrapCitiesStatisticsForTechnology(technologyName);
    }

    public List<CountryDto> itJobOffersInWorld(String technologyName) {
        return scrapTechnologyCountry.scrapCountriesStatisticsForTechnology(technologyName);
    }

    public List<CategoryDto> categoryStatistics(String location) {
        return scrapCategoryCity.scrapCategoryStatistics(location);
    }

}
