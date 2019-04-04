package com.Backend.model;

import lombok.Data;

@Data
public class City {

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    String name;
    int population;
    int adAmount;
    double AdPerCapita;

}
