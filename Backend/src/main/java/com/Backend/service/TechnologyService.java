package com.Backend.service;

import com.Backend.dto.TechnologyDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface TechnologyService {
    List<TechnologyDto> getTechnologyStatistics(ModelMap city);
    List<TechnologyDto> getLastDataFromDB();
}
