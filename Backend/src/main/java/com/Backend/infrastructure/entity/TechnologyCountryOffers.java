package com.Backend.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TechnologyCountryOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Country country;

    @ManyToOne
    Technology technology;

    LocalDate date;
    int linkedin;
    int indeed;

    public TechnologyCountryOffers(Country country, Technology technology, LocalDate date) {
        this.country = country;
        this.technology = technology;
        this.date = date;
    }
}
