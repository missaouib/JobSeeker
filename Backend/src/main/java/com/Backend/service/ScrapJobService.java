package com.Backend.service;

import com.Backend.entity.City;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.dto.CategoryDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ScrapJobService {
    List<City> getItJobOffersInPoland(ModelMap technology);
    List<Country> getItJobOffersInWorld(ModelMap technology);
    List<Technology> getTechnologyStatistics(ModelMap city);
    List<CategoryDto> getCategoryStatistics(ModelMap city);
}
