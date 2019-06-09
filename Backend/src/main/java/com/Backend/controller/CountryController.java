package com.Backend.controller;

import com.Backend.dto.CountryDto;
import com.Backend.service.CountryService;
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

    private CountryService countryService;

    CountryController (CountryService countryService){
        this.countryService = Objects.requireNonNull(countryService);
    }

    @PostMapping("/itJobOffersInWorld")
    public List<CountryDto> itJobOffersInWorld(@RequestBody ModelMap technology){
        return countryService.scrapItJobOffersInWorld(technology);
    }
}
