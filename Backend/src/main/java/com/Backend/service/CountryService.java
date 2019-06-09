package com.Backend.service;

import com.Backend.dto.CountryDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface CountryService {
    List<CountryDto> getItJobOffersInWorld(ModelMap technology);
    List<CountryDto> getLastDataFromDB();
}
