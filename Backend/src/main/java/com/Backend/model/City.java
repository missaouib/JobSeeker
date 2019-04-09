package com.Backend.model;

import lombok.Data;

@Data
public class City {

    public City(String name, int population, double areaSquareKilometers) {
        this.name = name;
        this.population = population;
        this.areaSquareKilometers = areaSquareKilometers;
    }

    String name;
    int population;
    int jobAmount;
    double jobOfferPer100kCitizens;
    double areaSquareKilometers;
    double destinyOfPopulation;

}
