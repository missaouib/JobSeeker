package com.Backend.service;

import com.Backend.model.City;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface AdService {
    List<City> getAdsinCities(ModelMap technology);
}
