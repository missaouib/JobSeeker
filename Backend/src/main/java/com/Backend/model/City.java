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
    double areaSquareKilometers;
    int adAmount;
    double adPer100kCitizens;

}
