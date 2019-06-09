package com.Backend.service.implementation;

import com.Backend.dto.TechnologyDto;
import com.Backend.service.TechnologyService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class TechnologyServiceImp implements TechnologyService {

    public TechnologyServiceImp() {
    }

    @Override
    public List<TechnologyDto> getTechnologyStatistics(ModelMap city) {
        return null;
    }

    @Override
    public List<TechnologyDto> getLastDataFromDB() {
        return null;
    }
}
