package com.Backend.controller;

import com.Backend.model.City;
import com.Backend.model.Technology;
import com.Backend.service.JobService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class HtmlController {

    private JobService jobService;

    HtmlController (JobService jobService){
        this.jobService = jobService;
    }

    @PostMapping("/technologyStatistics")
    public List<City> getCities(@RequestBody ModelMap technology) {
        return jobService.getCities(technology);
    }

    @PostMapping("/cityStatistics")
    public List<Technology> getTechnologies(@RequestBody ModelMap city){
        return jobService.getTechnologies(city);
    }
}
