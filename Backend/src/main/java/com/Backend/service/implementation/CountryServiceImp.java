package com.Backend.service.implementation;

import com.Backend.dto.CountryDto;
import com.Backend.service.CountryService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class CountryServiceImp implements CountryService {

    public CountryServiceImp() {
    }

    @Override
    public List<CountryDto> getItJobOffersInWorld(ModelMap technology) {
        return null;
    }

    @Override
    public List<CountryDto> getLastDataFromDB() {
        return null;
    }
}
