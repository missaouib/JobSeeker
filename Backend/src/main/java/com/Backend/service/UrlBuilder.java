package com.Backend.service;

public class UrlBuilder {

    public static String linkedinBuildUrlForCityAndCountry(String technologyName, String cityOrCountry) {
        String url = "https://www.linkedin.com/jobs/search?keywords=" + technologyName + "&location=" + cityOrCountry;

        switch (technologyName) {
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

        if (cityOrCountry.equals("poland") && !technologyName.equals("all it jobs") && !technologyName.equals("all jobs")) {
            url = "https://www.linkedin.com/jobs/" + technologyName + "-jobs-poland";
        }
        if (cityOrCountry.equals("poland") && technologyName.equals("c++")) {
            url = "https://www.linkedin.com/jobs/c++-jobs-poland";
        }

        return url;
    }

    public static String indeedBuildUrlLForCity(String technologyName, String cityName) {
        String url = "https://pl.indeed.com/Praca-" + technologyName + "-w-" + cityName;

        switch(technologyName){
            case "all it jobs":
                url = "https://pl.indeed.com/praca?q=IT&l=" + cityName;
                break;
            case "all jobs":
                url = "https://pl.indeed.com/praca?q=&l=" + cityName;
                break;
        }

        return url;
    }

    public static String indeedBuildUrlForCountry(String technologyName, String countryCode) {
        String url = "https://" + countryCode + ".indeed.com/" + technologyName + "-jobs";

        switch (countryCode) {
            case "us":
                url = "https://indeed.com/" + technologyName + "-jobs";
                break;
            case "my":
                url = "https://indeed.com.my/" + technologyName + "-jobs";
        }

        return url;
    }

    public static String indeedBuildUrlForCategoryForCity(String cityName, String categoryName) {
        String url = "https://pl.indeed.com/praca?q=" + categoryName + "&l=" + cityName;

        if (cityName.equals("poland")) {
            url = "https://pl.indeed.com/praca?q=" + categoryName;
        }

        return url;
    }

    public static String pracujBuildUrlForCity(String technologyName, String cityName) {
        String url = "https://www.pracuj.pl/praca/" + technologyName + ";kw/" + cityName + ";wp";

        switch (technologyName) {
            case "all jobs":
                url = "https://www.pracuj.pl/praca/" + cityName + ";wp";
                break;
            case "all it jobs":
                url = "https://www.pracuj.pl/praca/" + cityName + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016";
                break;
            case "c#":
                url = "https://www.pracuj.pl/praca/c%23;kw/" + cityName + ";wp";
                break;
        }

        if (cityName.equals("poland") && !technologyName.equals("all it jobs") && !technologyName.equals("all jobs")) {
            url = "https://www.pracuj.pl/praca/" + technologyName + ";kw";
        }

        return url;
    }

    public static String pracujBuildUrlForCategory(String cityName, String categoryName, int pracujId) {
        String url = "https://www.pracuj.pl/praca/" + cityName + ";wp/" + categoryName + ";cc," + pracujId;

        if (cityName.equals("poland")) {
            url = "https://www.pracuj.pl/praca/" + categoryName + ";cc," + pracujId;
        }
        return url;
    }

    public static String noFluffJobsBuildUrlForCity(String technologyName, String cityName) {
        return "https://nofluffjobs.com/api/search/posting?criteria=city=" + cityName + "+" + technologyName;
    }

    static String justJoinItBuildUrlForCity() {
        return "https://justjoin.it/api/offers";
    }

}
