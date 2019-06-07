package com.Backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class City {

    public City(String name, int population, double areaSquareKilometers) {
        this.name = name;
        this.population = population;
        this.area = areaSquareKilometers;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
}
