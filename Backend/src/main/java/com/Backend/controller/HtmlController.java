package com.Backend.controller;

import com.Backend.model.City;
import com.Backend.model.Country;
import com.Backend.model.Technology;
import com.Backend.model.dto.CategoryDto;
import com.Backend.service.JobService;
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

    private JobService jobService;

    HtmlController (JobService jobService){
        this.jobService = Objects.requireNonNull(jobService);
    }

    @PostMapping("/itJobOffersInPoland")
    public List<City> ItJobOffers(@RequestBody ModelMap technology) {
        return jobService.getItJobOffersInPoland(technology);
    }

    @PostMapping("/itJobOffersInWorld")
    public List<Country> itJobOffersInWorld(@RequestBody ModelMap technology){
        return jobService.getItJobOffersInWorld(technology);
    }

    @PostMapping("/technologyStatistics")
    public List<Technology> TechnologyStatistics(@RequestBody ModelMap city){
        return jobService.getTechnologyStatistics(city);
    }

    @PostMapping("/categoryStatistics")
    public List<CategoryDto> CategoryStatistics(@RequestBody ModelMap city){
        return jobService.getCategoryStatistics(city);
    }
}
