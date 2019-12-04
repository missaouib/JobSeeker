package com.Backend.service;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    public String linkedinBuildURLForCityAndCountry(String technology, String cityOrCountry){
        String dynamicURL = "https://www.linkedin.com/jobs/search?keywords=" + technology + "&location=" + cityOrCountry;

        switch(technology){
            case "all jobs":
                dynamicURL = "https://www.linkedin.com/jobs/search?keywords=&location=" + cityOrCountry;
                break;
            case "all it jobs":
                dynamicURL = "https://www.linkedin.com/jobs/search?location=" + cityOrCountry + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96";
                break;
            case "c++":
                dynamicURL = "https://www.linkedin.com/jobs/c++-jobs-" + cityOrCountry;
                break;
            case "c#":
                dynamicURL = "https://www.linkedin.com/jobs/search?keywords=C%23&location=" + cityOrCountry;
                break;
        }
        return dynamicURL;
    }

    public String indeedBuildURLForCity(String technlogy, String city){

    }

    public String indeedBuildURLForCountry(String technlogy, String city){

    }

    public String pracujBuildURLForCity(String technlogy, String city){

    }

    public String noFluffJobsBuildURLForCity(String technlogy, String city){

    }

    public String justJoinItBuildURLForCity(String technlogy, String city){

    }

}
