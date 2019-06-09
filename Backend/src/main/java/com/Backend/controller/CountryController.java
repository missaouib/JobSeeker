package com.Backend.controller;

import com.Backend.entity.Country;
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
public class CountryController {

    private ScrapJobService scrapJobService;

    CountryController (ScrapJobService scrapJobService){
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
    }

    @PostMapping("/itJobOffersInWorld")
    public List<Country> itJobOffersInWorld(@RequestBody ModelMap technology){
        return scrapJobService.getItJobOffersInWorld(technology);
    }
}
