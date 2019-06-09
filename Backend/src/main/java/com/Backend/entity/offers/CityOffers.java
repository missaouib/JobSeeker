package com.Backend.entity.offers;

import com.Backend.entity.City;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CityOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    City city;

    LocalDate date;
    int linkedin;
    int pracuj;
    int nofluffJobs;
    int justJoin;
    int total;
    double jobOfferPer100kCitizens;
}
