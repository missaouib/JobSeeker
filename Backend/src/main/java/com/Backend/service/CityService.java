package com.Backend.service;

import com.Backend.dto.CityDto;

import java.util.List;

public interface CityService {
    List<CityDto> scrapItJobOffersInPoland(String technology);
    List<CityDto> getItJobOffersInPoland(String technology);
}
