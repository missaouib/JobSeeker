package com.Backend.presentation;

import com.Backend.core.domain.DiagramHistory;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
public class DiagramController {

    DiagramHistory diagramHistory;

    @GetMapping("/ItJobsOfferInPolandDiagram")
    public void ItJobsOfferInPolandDiagram(@RequestParam("technology") String technology) {

    }

    @GetMapping("/itJobOffersInWorldDiagram")
    public void itJobOffersInWorldDiagram(@RequestParam("technology") String technology) {

    }

    @GetMapping("/categoryStatisticsInPolandDiagram")
    public void categoryStatisticsInPolandDiagram(@RequestParam("location") String location) {

    }

    @GetMapping("/technologyStatisticsInPolandDiagram")
    public void technologyStatisticsInPolandDiagram(@RequestParam("location") String location) {

    }

    @GetMapping("/technologyStatisticsInWorldDiagram")
    public void technologyStatisticsInWorldDiagram(@RequestParam("location") String location) {

    }

}
