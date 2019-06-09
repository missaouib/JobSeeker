package com.Backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Data
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    public City(String name) {
        this.name = name;
    }

    String name;
    int population;
    double area;
    int density;

//    int linkedinOffers;
//    int pracujOffers;
//    int noFluffJobsOffers;
//    int justJoinOffers;
//    int totalJobOffers;
}
