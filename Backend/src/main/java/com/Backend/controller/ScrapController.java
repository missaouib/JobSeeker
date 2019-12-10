package com.Backend.controller;

import com.Backend.dto.CityDto;
import com.Backend.service.ScrapCategoryService;
import com.Backend.service.ScrapTechnologyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ScrapController {

    private ScrapTechnologyService scrapTechnologyService;
    private ScrapCategoryService scrapCategoryService;

    public ScrapController(ScrapTechnologyService scrapTechnologyService, ScrapCategoryService scrapCategoryService) {
        this.scrapTechnologyService = scrapTechnologyService;
        this.scrapCategoryService = scrapCategoryService;
    }

    @GetMapping("/x")
    public List<CityDto> ItJobOffersInPoland(@RequestParam("technology") String technology) {
        return scrapTechnologyService.scrapTechnologyStatisticsForCities(technology);
    }

//    @GetMapping("/itJobOffersInWorld")
//    public List<CountryDto> itJobOffersInWorld(@RequestParam("technology") String technology){
//        return offersService.getItJobOffersInWorld(technology);
//    }
//
//    @GetMapping("/technologyStatistics")
//    public List<TechnologyDto> TechnologyStatistics(@RequestParam("location") String location) {
//        return offersService.getTechnologyStatistics(location);
//    }
//
//    @GetMapping("/categoryStatistics")
//    public List<CategoryDto> CategoryStatistics(@RequestParam("location") String location){
//        return scrapCategoryService.getCategoryStatistics(location);
//    }

}
