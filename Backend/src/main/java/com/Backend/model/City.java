package com.Backend.model;

import lombok.Data;

@Data
public class City {

    public City(String name, int population, double areaSquareKilometers, int averageFlatPrice) {
        this.name = name;
        this.population = population;
        this.averageFlatPrice = averageFlatPrice;
        this.areaSquareKilometers = areaSquareKilometers;
    }

    String name;
    int linkedinOffers;
    int pracujOffers;
    int noFluffJobsOffers;
    int justJoinOffers;
    int totalJobOffers;
    int population;
    int averageFlatPrice;
    int destinyOfPopulation;
    double areaSquareKilometers;
    double jobOfferPer100kCitizens;
}
