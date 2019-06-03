package com.Backend.model;

import lombok.Data;

@Data
public class Country {

    public Country(String name, int population, double areaSquareKilometers) {
        this.name = name;
        this.population = population;
        this.areaSquareKilometers = areaSquareKilometers;
    }

    String name;
    int linkedinOffers;
    int population;
    double areaSquareKilometers;
    int destinyOfPopulation;
    double jobOfferPer100kCitizens;
}
