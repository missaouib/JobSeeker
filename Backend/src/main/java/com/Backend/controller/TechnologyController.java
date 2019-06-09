package com.Backend.controller;

import com.Backend.entity.Technology;
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
public class TechnologyController {

    private ScrapJobService scrapJobService;

    TechnologyController (ScrapJobService scrapJobService){
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
    }

    @PostMapping("/technologyStatistics")
    public List<Technology> TechnologyStatistics(@RequestBody ModelMap city){
        return scrapJobService.getTechnologyStatistics(city);
    }
}
