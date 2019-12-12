package com.Backend.service;

public class UrlBuilder {

    public String linkedinBuildUrlForCityAndCountry(String technology, String cityOrCountry) {
        String url = "https://www.linkedin.com/jobs/search?keywords=" + technology + "&location=" + cityOrCountry;

        switch (technology) {
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

        if (cityOrCountry.equals("poland") && !technology.equals("all it jobs") && !technology.equals("all jobs")) {
            url = "https://www.linkedin.com/jobs/" + technology + "-jobs-poland";
        }
        if (cityOrCountry.equals("poland") && technology.equals("c++")) {
            url = "https://www.linkedin.com/jobs/c++-jobs-poland";
        } else if (technology.equals("c++")) {
            url = "https://www.linkedin.com/jobs/c++-jobs-" + technology;
        }

        return url;
    }

    public String indeedBuildUrlLForCity(String technology, String city) {
        return "https://pl.indeed.com/Praca-" + technology + "-w-" + city;
    }

    public String indeedBuildUrlForCountry(String technology, String countryCode) {
        String url = "https://" + countryCode + ".indeed.com/" + technology + "-jobs";

        switch (countryCode) {
            case "us":
                url = "https://indeed.com/" + technology + "-jobs";
                break;
            case "my":
                url = "https://indeed.com.my/" + technology + "-jobs";
        }
        return url;
    }

    public String pracujBuildUrlForCity(String technology, String city) {
        String url = "https://www.pracuj.pl/praca/" + technology + ";kw/" + city + ";wp";

        switch (technology) {
            case "all jobs":
                url = "https://www.pracuj.pl/praca/" + city + ";wp";
                break;
            case "all it jobs":
                url = "https://www.pracuj.pl/praca/" + city + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016";
                break;
            case "c#":
                url = "https://www.pracuj.pl/praca/c%23;kw/" + city + ";wp";
                break;
        }

        if (city.equals("poland") && !technology.equals("all it jobs") && !technology.equals("all jobs")) {
            url = "https://www.pracuj.pl/praca/" + technology + ";kw";
        }

        return url;
    }

    public String pracujBuildUrlForCategory(String city, String category, int pracujId) {
        String pracujDynamicURL = "https://www.pracuj.pl/praca/" + city + ";wp/" + category + ";cc," + pracujId;

        if(city.equals("all cities")) {
            pracujDynamicURL = "https://www.pracuj.pl/praca/" + category + ";cc," + pracujId;
        }
        return pracujDynamicURL;
    }

    public String noFluffJobsBuildUrlForCity(String technology, String city) {
        return "https://nofluffjobs.com/api/search/posting?criteria=city=" + city + "+" + technology;
        //return "https://nofluffjobs.com/api/posting";
    }

    public String justJoinItBuildUrlForCity() {
        return "https://justjoin.it/api/offers";
    }

}
