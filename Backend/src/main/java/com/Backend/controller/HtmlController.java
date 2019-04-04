package com.Backend.controller;

import com.Backend.model.City;
import com.Backend.service.AdService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class HtmlController {

    private AdService adService;

    HtmlController (AdService adService){
        this.adService = adService;
    }

    @GetMapping("/getAmountOfAdsForTechnology")
    public List<City> getTechnology(@RequestBody ModelMap technology) {
        return adService.getAdsinCities(technology);
    }

}
