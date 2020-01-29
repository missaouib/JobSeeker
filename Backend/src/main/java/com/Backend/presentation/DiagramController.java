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
    public List<DiagramDto> itJobOffersInWorldDiagram(@RequestParam("technology") String technologyName, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("portals") String portals) {
        List<String> portalNames = Arrays.asList(portals.split("\\s*, \\s*"));
        return diagramHistory.getItJobOffersInWorldDiagram(technologyName.toLowerCase(), portalNames, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }

    @GetMapping("/technologyStatisticsInPolandDiagram")
    public List<DiagramDto> technologyStatisticsInPolandDiagram(@RequestParam("location") String location, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("portals") String portals) {
        List<String> portalNames = Arrays.asList(portals.split("\\s*, \\s*"));
        return diagramHistory.getTechnologyStatsInPolandDiagram(location.toLowerCase(), portalNames, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }

    @GetMapping("/technologyStatisticsInWorldDiagram")
    public List<DiagramDto> technologyStatisticsInWorldDiagram(@RequestParam("location") String location, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("portals") String portals) {
        List<String> portalNames = Arrays.asList(portals.split("\\s*, \\s*"));
        return diagramHistory.getTechnologyStatsInWorldDiagram(location.toLowerCase(), portalNames, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }

    @GetMapping("/categoryStatisticsInPolandDiagramForIndeed")
    public List<DiagramDto> categoryStatisticsInPolandDiagramForIndeed(@RequestParam("location") String location, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("portals") String portals) {
        List<String> portalNames = Arrays.asList(portals.split("\\s*, \\s*"));
        return diagramHistory.getCategoryStatsInPolandDiagramForIndeed(location.toLowerCase(), portalNames, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }

    @GetMapping("/categoryStatisticsInPolandDiagramForPracuj")
    public List<DiagramDto> categoryStatisticsInPolandDiagramForPracuj(@RequestParam("location") String location, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("portals") String portals) {
        List<String> portalNames = Arrays.asList(portals.split("\\s*, \\s*"));
        return diagramHistory.getCategoryStatsInPolandDiagramForPracuj(location.toLowerCase(), portalNames, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }
}
