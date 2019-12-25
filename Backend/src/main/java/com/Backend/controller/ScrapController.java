package com.Backend.controller;

import com.Backend.domain.ScrapFacade;
import com.Backend.infrastructure.dto.CategoryDto;
import com.Backend.infrastructure.dto.CityDto;
import com.Backend.infrastructure.dto.CountryDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class ScrapController {

    private ScrapFacade scrapFacade;

    @GetMapping("/iTJobOffersInPoland")
    public List<CityDto> iTJobOffersInPoland(@RequestParam("technology") String technology) {
        return scrapFacade.ItJobsOffersInPoland(technology);
    }

    @GetMapping("/iTJobOffersInWorld")
    public List<CountryDto> iTJobOffersInWorld(@RequestParam("technology") String technology) {
        return scrapFacade.itJobOffersInWorld(technology);
    }

    @GetMapping("/categoryStatisticsInPoland")
    public List<CategoryDto> categoryStatisticsInPoland(@RequestParam("location") String location) {
        return scrapFacade.categoryStatisticsInPoland(location);
    }

//    @GetMapping("/technologyStatistics")
//    public List<TechnologyDto> TechnologyStatistics(@RequestParam("location") String location) {
//        return scrapTechnologyService.getTechnologyStatistics(location.toLowerCase());
//    }

}
