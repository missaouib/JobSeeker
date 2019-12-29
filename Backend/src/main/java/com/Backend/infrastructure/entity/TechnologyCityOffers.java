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
    private Long id;

    @ManyToOne
    private City city;

    @ManyToOne
    private Technology technology;

    private LocalDate date;
    private int linkedin;
    private int indeed;
    private int pracuj;
    private int noFluffJobs;
    private int justJoinIt;

    public TechnologyCityOffers(City city, Technology technology, LocalDate date) {
        this.city = city;
        this.technology = technology;
        this.date = date;
    }
}
