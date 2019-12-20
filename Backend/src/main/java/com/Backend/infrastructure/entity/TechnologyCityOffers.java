package com.Backend.infrastructure.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class TechnologyCityOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    City city;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Technology technology;

    LocalDate date;
    int linkedin;
    int indeed;
    int pracuj;
    int noFluffJobs;
    int justJoinIT;

    public TechnologyCityOffers(City city, Technology technology, LocalDate date) {
        this.city = city;
        this.technology = technology;
        this.date = date;
    }
}
