package com.Backend.controller;

import com.Backend.dto.TechnologyDto;
import com.Backend.service.TechnologyService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class TechnologyController {

    private TechnologyService technologyService;

    TechnologyController (TechnologyService technologyService){
        this.technologyService = Objects.requireNonNull(technologyService);
    }

    @PostMapping("/technologyStatistics")
    public List<TechnologyDto> TechnologyStatistics(@RequestBody ModelMap city){
        return technologyService.scrapTechnologyStatistics(city);
    }
}
