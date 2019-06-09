package com.Backend.entity.offers;

import com.Backend.entity.City;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
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
    int noFluffJobs;
    int justJoin;
    int total;
    double per100k;

    public CityOffers(City city, LocalDate date) {
        this.city = city;
        this.date = date;
    }
}
