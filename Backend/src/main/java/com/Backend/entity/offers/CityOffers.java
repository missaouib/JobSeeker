package com.Backend.entity.offers;

import com.Backend.entity.City;
import com.Backend.entity.Technology;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Technology technology;

    LocalDate date;
    int linkedin;
    int pracuj;
    int noFluffJobs;
    int justJoin;
    int total;
    double per100k;

    public CityOffers(City city, Technology technology, LocalDate date) {
        this.city = city;
        this.technology = technology;
        this.date = date;
    }
}
