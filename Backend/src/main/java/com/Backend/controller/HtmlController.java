package com.Backend.controller;

import com.Backend.entity.City;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.dto.CategoryDto;
import com.Backend.service.ScrapJobService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class HtmlController {

    private ScrapJobService scrapJobService;

    HtmlController (ScrapJobService scrapJobService){
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
    }

    @PostMapping("/itJobOffersInPoland")
    public List<City> ItJobOffers(@RequestBody ModelMap technology) {
        return scrapJobService.getItJobOffersInPoland(technology);
    }

    @PostMapping("/itJobOffersInWorld")
    public List<Country> itJobOffersInWorld(@RequestBody ModelMap technology){
        return scrapJobService.getItJobOffersInWorld(technology);
    }

    @PostMapping("/technologyStatistics")
    public List<Technology> TechnologyStatistics(@RequestBody ModelMap city){
        return scrapJobService.getTechnologyStatistics(city);
    }

    @PostMapping("/categoryStatistics")
    public List<CategoryDto> CategoryStatistics(@RequestBody ModelMap city){
        return scrapJobService.getCategoryStatistics(city);
    }
}
