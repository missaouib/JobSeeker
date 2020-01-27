package com.Backend.core.domain;

import com.Backend.infrastructure.dto.diagram.DiagramPersistenceDto;
import com.Backend.infrastructure.dto.diagram.DiagramDto;
import com.Backend.infrastructure.dto.diagram.Series;
import com.Backend.infrastructure.repository.CategoryOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
import com.Backend.infrastructure.repository.implementation.CustomOffersDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiagramHistory {

    CategoryOffersInPolandRepository categoryOffersInPolandRepository;
    TechnologyOffersInWorldRepository technologyOffersInWorldRepository;
    TechnologyOffersInPolandRepository technologyOffersInPolandRepository;
    CustomOffersDao customOffersDao;

    public List<DiagramDto> getItJobOffersInPolandDiagram(String technologyName, List<String> portalNames, LocalDate dateFrom, LocalDate dateTo) {
        List<DiagramPersistenceDto> dtos = getOffersFromSelectedPortals(customOffersDao.findDiagramItJobOffersInPoland(technologyName, dateFrom, dateTo), portalNames);
        return parseToDto(dtos);
    }

    public List<DiagramDto> getItJobOffersInWorldDiagram(String technologyName, List<String> portalNames, LocalDate dateFrom, LocalDate dateTo) {
        List<DiagramPersistenceDto> dtos = getOffersFromSelectedPortals(customOffersDao.findDiagramItJobOffersInWorld(technologyName, dateFrom, dateTo), portalNames);
        return parseToDto(dtos);
    }

    public List<DiagramDto> getTechnologyStatsInPolandDiagram(String cityName, List<String> portalNames, LocalDate dateFrom, LocalDate dateTo){
        List<DiagramPersistenceDto> dtos = getOffersFromSelectedPortals(customOffersDao.findDiagramTechnologyStatsInPoland(cityName, dateFrom, dateTo), portalNames);
        return parseToDto(dtos);
    }

    public List<DiagramDto> getTechnologyStatsInWorldDiagram(String countryName, List<String> portalNames, LocalDate dateFrom, LocalDate dateTo){
        List<DiagramPersistenceDto> dtos = getOffersFromSelectedPortals(customOffersDao.findDiagramTechnologyStatsInWorld(countryName, dateFrom, dateTo), portalNames);
        return parseToDto(dtos);
    }

    private List<DiagramDto> parseToDto(List<DiagramPersistenceDto> diagramPersistenceDto) {
        return diagramPersistenceDto.stream()
                .collect(Collectors.groupingBy(DiagramPersistenceDto::getName))
                .entrySet().stream()
                .map(entry -> new DiagramDto(entry.getKey(), getSeries(entry.getValue())))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Series> getSeries(List<DiagramPersistenceDto> value) {
        return value.stream()
                .map(dto -> new Series(dto.getDate(), dto.getSelectedOffers()))
                .collect(Collectors.toList());
    }

    private List<DiagramPersistenceDto> getOffersFromSelectedPortals(List<DiagramPersistenceDto> dtos, List<String> portalNames) {
        dtos.forEach(dto -> {
            portalNames.forEach(name -> {
                if (name.equals("linkedin")) {
                    dto.addToOffers(dto.getLinkedin());
                }
                if (name.equals("indeed")) {
                    dto.addToOffers(dto.getIndeed());
                }
                if (name.equals("pracuj")) {
                    dto.addToOffers(dto.getPracuj());
                }
                if (name.equals("noFluffJobs")) {
                    dto.addToOffers(dto.getNoFluffJobs());
                }
                if (name.equals("justJoinIt")) {
                    dto.addToOffers(dto.getJustJoinIt());
                }
            });
        });
        return dtos;
    }

}
