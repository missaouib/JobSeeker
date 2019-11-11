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

    @GetMapping("/indeed")
    public String seleniumTest() throws MalformedURLException {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("headless", true);
        WebDriver webDriver = new RemoteWebDriver(new URL("http://ec2-18-197-75-156.eu-central-1.compute.amazonaws.com:4444/wd/hub"), capabilities);

        webDriver.get("https://pl.indeed.com/jobs?q=java+&l=krak%C3%B3w");
        String indeed =  webDriver.findElement(By.id("searchCountPages")).getText();
        webDriver.quit();

        return indeed;
    }

    @GetMapping("/glassdor")
    public String glassdor() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("headless", true);
        WebDriver webDriver = new RemoteWebDriver(new URL("http://localhost:3444/wd/hub"), capabilities);

        webDriver.get("https://www.glassdoor.com/Job/jobs.htm?suggestCount=0&suggestChosen=false&clickSource=searchBtn&typedKeyword=java&sc.keyword=java&locT=C&locId=3017091&jobType=");
        String glassdor =  webDriver.findElement(By.className("jobsCount")).getText();
        webDriver.quit();

        return glassdor;
    }

    @GetMapping("/linkedin")
    public String linkedin() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("headless", true);
        WebDriver webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        webDriver.get("https://www.linkedin.com/jobs/search?keywords=java&location=Krakow%20Metropolitan%20Area&trk=homepage-basic_jobs-search-bar_search-submit&redirect=false&position=1&pageNum=0");
        String linkedin =  webDriver.findElement(By.className("results-context-header__job-count")).getText();

        webDriver.quit();
        return linkedin;
    }

}
