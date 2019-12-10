package com.Backend.entity.offers;

import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class TechnologyCountryOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Country country;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
