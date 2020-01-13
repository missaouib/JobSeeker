package com.Backend.core.domain;

import com.Backend.infrastructure.dto.DiagramDto;
import com.Backend.infrastructure.repository.CategoryOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiagramHistory {

    CategoryOffersInPolandRepository categoryOffersInPolandRepository;
    TechnologyOffersInWorldRepository technologyOffersInWorldRepository;
    TechnologyOffersInPolandRepository technologyOffersInPolandRepository;

    public List<DiagramDto> getItJobOffersInPolandDiagram(String technology, String fromDate, String toDate){
        return new ArrayList<>();
    }

}
