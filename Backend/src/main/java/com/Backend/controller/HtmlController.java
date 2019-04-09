package com.Backend.controller;

import com.Backend.model.City;
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

    @PostMapping("/getJobsOffers")
    public List<City> getJobOffers(@RequestBody ModelMap technology) {
        return jobService.getJobs(technology);
    }

}
