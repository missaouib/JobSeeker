package com.Backend.entity.offers;

import com.Backend.entity.Country;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class CountryOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Country country;

    LocalDate date;
    int linkedin;
    double per100k;

    public CountryOffers(Country country, LocalDate date) {
        this.country = country;
        this.date = date;
    }
}
