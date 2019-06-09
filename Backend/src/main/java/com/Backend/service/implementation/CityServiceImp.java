package com.Backend.service.implementation;

import com.Backend.dto.CityDto;
import com.Backend.service.CityService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class CityServiceImp implements CityService {

    public CityServiceImp() {
    }

    @Override
    public List<CityDto> scrapItJobOffersInPoland(ModelMap technology) {
        return null;
    }

    @Override
    public List<CityDto> getLastDataFromDB() {
        return null;
    }
}
