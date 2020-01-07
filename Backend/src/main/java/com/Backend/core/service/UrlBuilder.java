package com.Backend.core.service;

public class UrlBuilder {

    public static String linkedinBuildUrlForCityAndCountry(String technologyName, String cityOrCountry) {
        String url = "https://www.linkedin.com/jobs/" + technologyName + "-jobs-" + cityOrCountry;

        if ("c#".equals(technologyName)) {
            url = "https://www.linkedin.com/jobs/search?keywords=C%23&location=" + cityOrCountry;
        }

        return url;
    }

    public static String indeedBuildUrlLForCity(String technologyName, String cityName) {
        return "https://pl.indeed.com/Praca-" + technologyName + "-w-" + cityName;
    }

    public static String indeedBuildUrlForCountry(String technologyName, String countryCode) {
        String url = "https://" + countryCode + ".indeed.com/" + technologyName + "-jobs";

        if ("us".equals(countryCode)) {
            url = "https://indeed.com/" + technologyName + "-jobs";
        }

        return url;
    }

    public static String indeedBuildUrlForCategoryForCity(String cityName, String categoryName) {
        return "https://pl.indeed.com/praca?q=" + categoryName + "&l=" + cityName;
    }

    public static String pracujBuildUrlForCity(String technologyName, String cityName) {
        String url = "https://www.pracuj.pl/praca/" + technologyName + ";kw/" + cityName + ";wp";

        switch (technologyName) {
            case "c++":
                url = "https://www.pracuj.pl/praca/c%2b%2b/" + cityName + ";wp";
                break;
            case "c#":
                url = "https://www.pracuj.pl/praca/c%23;kw/" + cityName + ";wp";
                break;
        }

        return url;
    }

    public static String pracujBuildUrlForCategory(String cityName, String categoryName, int pracujId) {
        return "https://www.pracuj.pl/praca/" + cityName + ";wp/" + categoryName + ";cc," + pracujId;
    }

    public static String noFluffJobsBuildUrlForCity(String technologyName, String cityName) {
        return "https://nofluffjobs.com/api/search/posting?criteria=city=" + cityName + "+" + technologyName;
    }

    static String justJoinItBuildUrlForCity() {
        return "https://justjoin.it/api/offers";
    }

}
