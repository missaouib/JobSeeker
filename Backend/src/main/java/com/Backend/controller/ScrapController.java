package com.Backend.controller;

import com.Backend.dto.CategoryDto;
import com.Backend.service.CategoryService;
import com.Backend.service.OffersService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class OffersController {

    private OffersService offersService;
    private CategoryService categoryService;

    public OffersController(OffersService offersService, CategoryService categoryService) {
        this.offersService = offersService;
        this.categoryService = categoryService;
    }

//    @GetMapping("/itJobOffersInPoland")
//    public List<CityDto> ItJobOffers(@RequestParam("technology") String technology) {
//        return offersService.getItJobOffersInPoland(technology);
//    }
//
//    @GetMapping("/itJobOffersInWorld")
//    public List<CountryDto> itJobOffersInWorld(@RequestParam("technology") String technology){
//        return offersService.getItJobOffersInWorld(technology);
//    }
//
//    @GetMapping("/technologyStatistics")
//    public List<TechnologyDto> TechnologyStatistics(@RequestParam("location") String location) {
//        return offersService.getTechnologyStatistics(location);
//    }

    @GetMapping("/categoryStatistics")
    public List<CategoryDto> CategoryStatistics(@RequestParam("location") String location){
        return categoryService.getCategoryStatistics(location);
    }

}
