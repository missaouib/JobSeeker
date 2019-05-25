package com.Backend.service;

import com.Backend.model.Category;
import com.Backend.model.City;
import com.Backend.model.Technology;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface JobService {
    List<City> getItJobOffers(ModelMap technology);
    List<Technology> getTechnologyStatistics(ModelMap city);
    List<Category> getCategoryStatistics(ModelMap city);
}
