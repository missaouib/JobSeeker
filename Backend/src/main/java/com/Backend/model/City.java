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
    int population;
    int jobAmount;
    int averageFlatPrice;
    double jobOfferPer100kCitizens;
    double areaSquareKilometers;
    double destinyOfPopulation;

}
