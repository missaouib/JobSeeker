package com.Backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Country {

    public Country(String name, int population, double areaSquareKilometers) {
        this.name = name;
        this.population = population;
        this.areaSquareKilometers = areaSquareKilometers;
    }

    @Id
    @GeneratedValue
    Long id;

    String name;
    int linkedinOffers;
    int population;
    double destinyOfPopulation;
    double areaSquareKilometers;
    double jobOfferPer100kCitizens;
}
