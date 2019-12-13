package com.Backend.controller;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CityDto;
import com.Backend.dto.CountryDto;
import com.Backend.service.scrap.ScrapFacade;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ScrapController {

    private ScrapFacade scrapFacade;

    public ScrapController(ScrapFacade scrapFacade) {
        this.scrapFacade = scrapFacade;
    }

    @GetMapping("/iTJobOffersInPoland")
    public List<CityDto> iTJobOffersInPoland(@RequestParam("technology") String technology) {
        return scrapFacade.ItJobsOffersInPoland(technology.toLowerCase());
    }

    @GetMapping("/iTJobOffersInWorld")
    public List<CountryDto> iTJobOffersInWorld(@RequestParam("technology") String technology){
        return scrapFacade.itJobOffersInWorld(technology.toLowerCase());
    }

    @GetMapping("/categoryStatisticsInPoland")
    public List<CategoryDto> categoryStatisticsInPoland(@RequestParam("location") String location){
        return scrapFacade.categoryStatisticsInPoland(location.toLowerCase());
    }

    @GetMapping("/categoryStatisticsInWorld")
    public List<CategoryDto> categoryStatisticsInWorld(@RequestParam("location") String location){
        return scrapFacade.categoryStatisticsInWorld(location.toLowerCase());
    }

//    @GetMapping("/technologyStatistics")
//    public List<TechnologyDto> TechnologyStatistics(@RequestParam("location") String location) {
//        return scrapTechnologyService.getTechnologyStatistics(location.toLowerCase());
//    }

}
