package com.Backend.core.domain;

import com.Backend.infrastructure.dto.DiagramDto;
import com.Backend.infrastructure.dto.DiagramPersistenceDto;
import com.Backend.infrastructure.repository.CategoryOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiagramHistory {

    CategoryOffersInPolandRepository categoryOffersInPolandRepository;
    TechnologyOffersInWorldRepository technologyOffersInWorldRepository;
    TechnologyOffersInPolandRepository technologyOffersInPolandRepository;

    public List<DiagramDto> getItJobOffersInPolandDiagram(String technologyName, LocalDate dateFrom, LocalDate dateTo) {
        return parseToDto(technologyOffersInPolandRepository.findTechnologiesBetweenDate(technologyName, dateFrom, dateTo));
    }

    private List<DiagramDto> parseToDto(List<DiagramPersistenceDto> diagramPersistenceDto) {
        return diagramPersistenceDto.stream()
                .collect(Collectors.groupingBy(DiagramPersistenceDto::getName))
                .entrySet().stream()
                .map(entry -> new DiagramDto(entry.getKey(), getSeries(entry.getValue())))
                .collect(Collectors.toList())
                .stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<DiagramDto.Series> getSeries(List<DiagramPersistenceDto> value) {
        return value.stream()
                .map(dto -> new DiagramDto.Series(dto.getDate(), dto.getOffers()))
                .collect(Collectors.toList());
    }

}
