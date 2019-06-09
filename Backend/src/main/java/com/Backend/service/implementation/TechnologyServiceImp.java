package com.Backend.service.implementation;

import com.Backend.dto.TechnologyDto;
import com.Backend.service.ScrapJobService;
import com.Backend.service.TechnologyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class TechnologyServiceImp implements TechnologyService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;

    public List<TechnologyDto> scrapTechnologyStatistics(ModelMap city) {
        return null;
    }

    public List<TechnologyDto> getLastDataFromDB() {
        return null;
    }
}
