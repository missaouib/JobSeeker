package com.Backend.controller;

import com.Backend.dto.CategoryDto;
import com.Backend.service.ScrapJobService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class CategoryController {

    private ScrapJobService scrapJobService;

    CategoryController (ScrapJobService scrapJobService){
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
    }

    @PostMapping("/categoryStatistics")
    public List<CategoryDto> CategoryStatistics(@RequestBody ModelMap city){
        return scrapJobService.getCategoryStatistics(city);
    }
}
