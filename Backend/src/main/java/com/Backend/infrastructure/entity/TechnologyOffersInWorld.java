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
public class TechnologyOffersInWorld {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Country country;

    @ManyToOne
    private Technology technology;

    private LocalDate date;
    private int linkedin;
    private int indeed;

    public TechnologyOffersInWorld(Country country, Technology technology, LocalDate date) {
        this.country = country;
        this.technology = technology;
        this.date = date;
    }
}
