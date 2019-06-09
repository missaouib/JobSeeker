package com.Backend.entity.offers;

import com.Backend.entity.Technology;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TechnologyOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Technology technology;
    LocalDate date;
    int linkedin;
    int pracuj;
    int nofluffJobs;
    int justJoin;
    int total;
}
