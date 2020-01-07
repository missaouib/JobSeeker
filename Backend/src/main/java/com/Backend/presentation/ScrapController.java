package com.Backend.presentation;

import com.Backend.core.domain.ScrapFacade;
import com.Backend.infrastructure.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class ScrapController {

    private ScrapFacade scrapFacade;

    @GetMapping("/itJobOffersInPoland")
    public List<JobsOffersInPolandDto> iTJobOffersInPoland(@RequestParam("technology") String technology) {
        return scrapFacade.ItJobsOffersInPoland(technology, new ArrayList<>());
    }

    @GetMapping("/itJobOffersInWorld")
    public List<JobsOffersInWorldDto> iTJobOffersInWorld(@RequestParam("technology") String technology) {
        return scrapFacade.itJobOffersInWorld(technology);
    }

    @GetMapping("/categoryStatisticsInPoland")
    public List<CategoryStatisticsInPolandDto> categoryStatisticsInPoland(@RequestParam("location") String location) {
        return scrapFacade.categoryStatisticsInPoland(location);
    }

    @GetMapping("/technologyStatisticsInPoland")
    public List<TechnologyStatisticsInPolandDto> TechnologyStatisticsInPoland(@RequestParam("location") String location) {
        return scrapFacade.technologyStatisticsInPoland(location.toLowerCase());
    }

    @GetMapping("/technologyStatisticsInWorld")
    public List<TechnologyStatisticsInWorldDto> TechnologyStatisticsInWorld(@RequestParam("location") String location) {
        return scrapFacade.technologyStatisticsInWorld(location.toLowerCase());
    }
}
