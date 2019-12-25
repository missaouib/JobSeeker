package com.Backend.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TechnologyCityOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    City city;
    LocalDate date;
    int linkedin;
    int indeed;
    int pracuj;
    int noFluffJobs;
    int justJoinIT;

    @ManyToOne
    private Technology technology;

    public TechnologyCityOffers(City city, Technology technology, LocalDate date) {
        this.city = city;
        this.technology = technology;
        this.date = date;
    }
}
