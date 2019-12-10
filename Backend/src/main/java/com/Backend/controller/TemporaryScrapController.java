package com.Backend.controller;

import com.Backend.dto.CityDto;
import com.Backend.service.CategoryService;
import com.Backend.service.ScrapService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TemporaryScrapController {

    private ScrapService scrapService;
    private CategoryService categoryService;

    public TemporaryScrapController(ScrapService scrapService, CategoryService categoryService) {
        this.scrapService = scrapService;
        this.categoryService = categoryService;
    }

    @GetMapping("/x")
    public List<CityDto> ItJobOffers(@RequestParam("technology") String technology) {
        return scrapService.scrapTechnologyStatisticsForCities(technology);
    }

//    @GetMapping("/itJobOffersInWorld")
//    public List<CountryDto> itJobOffersInWorld(@RequestParam("technology") String technology){
//        return scrapService.getItJobOffersInWorld(technology);
//    }
//
//    @GetMapping("/technologyStatistics")
//    public List<TechnologyDto> TechnologyStatistics(@RequestParam("location") String location) {
//        return scrapService.getTechnologyStatistics(location);
//    }

//    @GetMapping("/categoryStatistics")
//    public List<CategoryDto> CategoryStatistics(@RequestParam("location") String location){
//        return categoryService.getCategoryStatistics(location);
//    }

}
