package com.Backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Technology {

    public Technology(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Id
    @GeneratedValue
    Long id;

    String name;
    String type;
    int linkedinOffers;
    int pracujOffers;
    int noFluffJobsOffers;
    int justJoinOffers;
    int totalJobOffers;
}
