package com.Backend.controller;

import com.Backend.dto.CityDto;
import com.Backend.service.CityService;
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

    private CityService cityService;

    CityController (CityService cityService){
        this.cityService = Objects.requireNonNull(cityService);
    }

    @PostMapping("/itJobOffersInPoland")
    public List<CityDto> ItJobOffers(@RequestBody ModelMap technology) {
        String selectedTechnology = technology.get("technology").toString();
        return cityService.scrapItJobOffersInPoland(selectedTechnology);
    }
}
