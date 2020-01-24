package com.Backend.core.domain;

import com.Backend.infrastructure.dto.DiagramDto;
import com.Backend.infrastructure.dto.DiagramPersistenceDto997;
import com.Backend.infrastructure.repository.CategoryOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
import com.Backend.infrastructure.repository.implementation.TechnologyOffersInPolandDao;
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
    TechnologyOffersInPolandDao technologyOffersInPolandDao;

    public List<DiagramDto> getItJobOffersInPolandDiagram(String technologyName, List<String> portalNames, LocalDate dateFrom, LocalDate dateTo) {
        String technologyNameLower = technologyName.toLowerCase();
        technologyOffersInPolandDao.findTechnologyOffersInPolandByPortal(technologyNameLower, portalNames, dateFrom, dateTo);
        return parseToDto(technologyOffersInPolandRepository.findTechnologiesBetweenDate(technologyNameLower, dateFrom, dateTo));
    }

    private List<DiagramDto> parseToDto(List<DiagramPersistenceDto997> diagramPersistenceDto997) {
        return diagramPersistenceDto997.stream()
                .collect(Collectors.groupingBy(DiagramPersistenceDto997::getName))
                .entrySet().stream()
                .map(entry -> new DiagramDto(entry.getKey(), getSeries(entry.getValue())))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<DiagramDto.Series> getSeries(List<DiagramPersistenceDto997> value) {
        return value.stream()
                .map(dto -> new DiagramDto.Series(dto.getDate(), dto.getOffers()))
                .collect(Collectors.toList());
    }

}
