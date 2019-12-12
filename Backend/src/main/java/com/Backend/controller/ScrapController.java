package com.Backend.controller;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CityDto;
import com.Backend.service.scrap.ScrapCategoryCity;
import com.Backend.service.scrap.ScrapTechnologyCity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class ScrapController {

    private ScrapTechnologyCity scrapTechnologyCity;
    private ScrapCategoryCity scrapCategoryCity;

    public ScrapController(ScrapTechnologyCity scrapTechnologyCity, ScrapCategoryCity scrapCategoryCity) {
        this.scrapTechnologyCity = Objects.requireNonNull(scrapTechnologyCity);
        this.scrapCategoryCity = Objects.requireNonNull(scrapCategoryCity);
    }

    @GetMapping("/x")
    public List<CityDto> ItJobOffersInPoland(@RequestParam("technology") String technology) {
        return scrapTechnologyCity.scrapCitiesStatisticsForTechnology(technology.toLowerCase());
    }

//    @GetMapping("/itJobOffersInWorld")
//    public List<CountryDto> itJobOffersInWorld(@RequestParam("technology") String technology){
//        return scrapTechnologyCity.scrapTechnologyStatisticsForCountries(technology.toLowerCase());
//    }

//    @GetMapping("/technologyStatistics")
//    public List<TechnologyDto> TechnologyStatistics(@RequestParam("location") String location) {
//        return scrapTechnologyService.getTechnologyStatistics(location.toLowerCase());
//    }

    @GetMapping("/categoryStatistics")
    public List<CategoryDto> CategoryStatistics(@RequestParam("location") String location){
        return scrapCategoryCity.getCategoryStatistics(location.toLowerCase());
    }

}
