package com.Backend.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@CrossOrigin
@RestController
public class SeleniumController {

    //Add bean config
    //Change to chrome
    //Change code
    //Clean build.gradle

    @Value("${aws.selenium.url}")
    private String seleniumUrl;

    @GetMapping("/google")
    public String googleTest() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        //ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        WebDriver webDriver = new RemoteWebDriver(new URL(seleniumUrl), options);
        webDriver.get("https://www.google.com/");
        String indeed =  webDriver.findElement(By.className("Q8LRLc")).getText();
        webDriver.quit();

        return indeed;
    }

    @GetMapping("/indeed")
    public String indeedTest() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("headless");

        WebDriver webDriver = new RemoteWebDriver(new URL(seleniumUrl), options);
        webDriver.get("https://pl.indeed.com/jobs?q=java+&l=krak%C3%B3w");
        String indeed =  webDriver.findElement(By.id("searchCountPages")).getText();
        webDriver.quit();

        return indeed;
    }

    @GetMapping("/glassdor")
    public String glassdor() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("headless");

        WebDriver webDriver = new RemoteWebDriver(new URL(seleniumUrl), options);
        webDriver.get("https://www.glassdoor.com/Job/jobs.htm?suggestCount=0&suggestChosen=false&clickSource=searchBtn&typedKeyword=java&sc.keyword=java&locT=C&locId=3017091&jobType=");
        String glassdor =  webDriver.findElement(By.className("jobsCount")).getText();
        webDriver.quit();

        return glassdor;
    }

    @GetMapping("/linkedin")
    public String linkedin() throws MalformedURLException {

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("headless");

        WebDriver webDriver = new RemoteWebDriver(new URL(seleniumUrl), options);
        webDriver.get("https://www.linkedin.com/jobs/search?keywords=java&location=Krakow%20Metropolitan%20Area&trk=homepage-basic_jobs-search-bar_search-submit&redirect=false&position=1&pageNum=0");
        String linkedin =  webDriver.findElement(By.className("filter-list__label-count")).getText();

        webDriver.quit();
        return linkedin;
    }
}
