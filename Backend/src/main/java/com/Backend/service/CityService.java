package com.Backend.service;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CityDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface CityService {
    List<CityDto> scrapItJobOffersInPoland(String technology);
    List<CityDto> getItJobOffersInPoland(String technology);
}
