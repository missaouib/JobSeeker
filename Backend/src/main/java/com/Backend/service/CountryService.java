package com.Backend.service;

import com.Backend.dto.CountryDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface CountryService {
    List<CountryDto> scrapItJobOffersInWorld(String technology);
}
