package com.Backend.service;

import com.Backend.model.City;
import com.Backend.model.Technology;
import com.Backend.model.dto.CategoryDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface JobService {
    List<City> getItJobOffers(ModelMap technology);
    List<Technology> getTechnologyStatistics(ModelMap city);
    List<CategoryDto> getCategoryStatistics(ModelMap city);
}
