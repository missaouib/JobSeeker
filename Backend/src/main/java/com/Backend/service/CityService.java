package com.Backend.service;

import com.Backend.dto.CityDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface CityService {
    List<CityDto> scrapItJobOffersInPoland(ModelMap technology);
}
