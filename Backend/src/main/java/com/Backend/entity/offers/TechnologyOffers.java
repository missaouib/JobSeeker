package com.Backend.entity.offers;

import com.Backend.entity.City;
import com.Backend.entity.Technology;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class TechnologyOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Technology technology;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    City city;

    LocalDate date;
    int linkedin;
    int indeed;
    int pracuj;
    int noFluffJobs;
    int justJoin;

    public TechnologyOffers(Technology technology, City city, LocalDate date) {
        this.technology = technology;
        this.city = city;
        this.date = date;
    }
}
