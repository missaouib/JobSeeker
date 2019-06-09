package com.Backend.controller;

import com.Backend.entity.City;
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
public class CityController {

    private ScrapJobService scrapJobService;

    CityController (ScrapJobService scrapJobService){
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
    }

    @PostMapping("/itJobOffersInPoland")
    public List<City> ItJobOffers(@RequestBody ModelMap technology) {
        return scrapJobService.getItJobOffersInPoland(technology);
    }
}
