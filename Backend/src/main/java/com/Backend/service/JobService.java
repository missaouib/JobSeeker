package com.Backend.service;

import com.Backend.model.City;
import com.Backend.model.Technology;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface JobService {
    List<City> getJobs(ModelMap technology);
    List<Technology> getTechnologies(ModelMap city);
}
