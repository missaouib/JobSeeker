package com.Backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Technology {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    String type;
//    int linkedinOffers;
//    int pracujOffers;
//    int noFluffJobsOffers;
//    int justJoinOffers;
//    int totalJobOffers;
}
