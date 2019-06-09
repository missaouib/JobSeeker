package com.Backend.controller;

import com.Backend.dto.CategoryDto;
import com.Backend.service.CategoryService;
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

    private CategoryService categoryService;

    CategoryController (CategoryService categoryService){
        this.categoryService = Objects.requireNonNull(categoryService);
    }

    @PostMapping("/categoryStatistics")
    public List<CategoryDto> CategoryStatistics(@RequestBody ModelMap city){
        return categoryService.scrapCategoryStatistics(city);
    }
}
