package com.Backend.entity.offers;

import com.Backend.entity.Country;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CountryOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Country country;
    LocalDate date;
    int linkedin;
    double jobOfferPer100kCitizens;
}
