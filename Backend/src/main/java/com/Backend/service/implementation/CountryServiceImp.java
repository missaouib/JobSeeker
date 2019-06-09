package com.Backend.service.implementation;

import com.Backend.dto.CountryDto;
import com.Backend.service.CountryService;
import com.Backend.service.ScrapJobService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class CountryServiceImp implements CountryService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;

    public List<CountryDto> scrapItJobOffersInWorld(ModelMap technology) {
        return null;
    }

    public List<CountryDto> getLastDataFromDB() {
        return null;
    }
}
