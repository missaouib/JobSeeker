package com.Backend.service;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    public String linkedinBuildUrlForCityAndCountry(String technology, String cityOrCountry){
        String url = "https://www.linkedin.com/jobs/search?keywords=" + technology + "&location=" + cityOrCountry;

        switch(technology){
            case "all jobs":
                url = "https://www.linkedin.com/jobs/search?keywords=&location=" + cityOrCountry;
                break;
            case "all it jobs":
                url = "https://www.linkedin.com/jobs/search?location=" + cityOrCountry + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96";
                break;
            case "c++":
                url = "https://www.linkedin.com/jobs/c++-jobs-" + cityOrCountry;
                break;
            case "c#":
                url = "https://www.linkedin.com/jobs/search?keywords=C%23&location=" + cityOrCountry;
                break;
        }
        return url;
    }

    public String indeedBuildUrlLForCity(String technlogy, String city){
        return "https://pl.indeed.com/Praca-" + technlogy + "-w-" + city;
    }

    public String indeedBuildUrlForCountry(String technlogy, String countryCode){
        String url = "https://" + countryCode + ".indeed.com/" + technlogy + "-jobs";

        switch(countryCode) {
            case "us":
                url = "https://indeed.com/" + technlogy + "-jobs";
                break;
            case "my":
                url = "https://indeed.com.my/" + technlogy + "-jobs";
        }
        return url;
    }

    public String pracujBuildUrlForCity(String technlogy, String city){
        String url = "https://www.pracuj.pl/praca/" + technlogy + ";kw/" + city + ";wp";

        switch(technlogy){
            case "all jobs":
                url = "https://www.pracuj.pl/praca/" + city + ";wp";
                break;
            case "all it jobs":
                url = "https://www.pracuj.pl/praca/" + city + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016";
            case "c#":
                url = "https://www.pracuj.pl/praca/c%23;kw/" + city + ";wp";
                break;
        }
        return url;
    }

    public String noFluffJobsBuildUrlForCity(String technlogy, String city){
        return "https://nofluffjobs.com/api/posting";
    }

    public String justJoinItBuildUrlForCity(String technlogy, String city){
        return "https://justjoin.it/api/offers";
    }

}
