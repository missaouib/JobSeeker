package com.Backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class City {

    public City(String name, int population, double areaSquareKilometers, int averageFlatPrice) {
        this.name = name;
        this.population = population;
        this.averageFlatPrice = averageFlatPrice;
        this.area = areaSquareKilometers;
    }

    @Id
    @GeneratedValue
    Long id;

    String name;
    int population;
    double area;
    int density;

    int linkedinOffers;
    int pracujOffers;
    int noFluffJobsOffers;
    int justJoinOffers;
    int totalJobOffers;
    int averageFlatPrice;
    double jobOfferPer100kCitizens;
}
