package com.Backend.controller;

import com.Backend.dto.CategoryDto;
import com.Backend.service.CategoryService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URL;
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
        String selectedCityUTF8 = city.get("city").toString();
        return categoryService.getCategoryStatistics(selectedCityUTF8);
    }
}
