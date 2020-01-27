package com.Backend.presentation;

import com.Backend.core.domain.DiagramHistory;
import com.Backend.infrastructure.dto.diagram.DiagramDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class DiagramController {

    DiagramHistory diagramHistory;

    @GetMapping("/ItJobsOfferInPolandDiagram")
    public List<DiagramDto> ItJobsOfferInPolandDiagram(@RequestParam("technology") String technologyName, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("portals") String portals) {
        List<String> portalNames = Arrays.asList(portals.split("\\s*, \\s*"));
        return diagramHistory.getItJobOffersInPolandDiagram(technologyName.toLowerCase(), portalNames, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }

    @GetMapping("/itJobOffersInWorldDiagram")
    public void itJobOffersInWorldDiagram(@RequestParam("technology") String technology) {

    }

    @GetMapping("/technologyStatisticsInPolandDiagram")
    public void technologyStatisticsInPolandDiagram(@RequestParam("location") String location) {

    }

    @GetMapping("/technologyStatisticsInWorldDiagram")
    public void technologyStatisticsInWorldDiagram(@RequestParam("location") String location) {

    }

    @GetMapping("/categoryStatisticsInPolandDiagram")
    public void categoryStatisticsInPolandDiagram(@RequestParam("location") String location) {

    }
}
