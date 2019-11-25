//package com.Backend.controller;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin
//@RestController
//public class SeleniumController {
//
//    @Value("${aws.selenium.url}")
//    private String seleniumUrl;
//
//    private WebDriver webDriver;
//
//    public SeleniumController(WebDriver webDriver) {
//        this.webDriver = webDriver;
//    }
//
//    @GetMapping("/google")
//    public String googleTest() throws MalformedURLException {
//        webDriver.get("https://www.google.com/");
//        return webDriver.findElement(By.className("Q8LRLc")).getText();
//    }
//
//    @GetMapping("/indeed")
//    public String indeedTest() throws MalformedURLException {
//        webDriver.get("https://pl.indeed.com/jobs?q=java+&l=krak%C3%B3w");
//        return webDriver.findElement(By.id("searchCountPages")).getText();
//    }
//
//    @GetMapping("/glassdor")
//    public String glassdor() throws MalformedURLException {
//        webDriver.get("https://www.glassdoor.com/Job/jobs.htm?suggestCount=0&suggestChosen=false&clickSource=searchBtn&typedKeyword=java&sc.keyword=java&locT=C&locId=3017091&jobType=");
//        return webDriver.getPageSource();
//        //return webDriver.findElement(By.className("jobsCount")).getText();
//    }
//
//    @GetMapping("/linkedin")
//    public String linkedin() throws MalformedURLException {
//        webDriver.get("https://www.linkedin.com/jobs/search?keywords=java&location=Krakow%20Metropolitan%20Area&trk=homepage-basic_jobs-search-bar_search-submit&redirect=false&position=1&pageNum=0");
//        return webDriver.getPageSource();
//        //return webDriver.findElement(By.className("filter-list__label-count")).getText();
//    }
//}
