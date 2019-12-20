package com.Backend.infrastructure.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@NoArgsConstructor
public class CategoryCountryOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Country country;

    private LocalDate date;
    private int indeed;

    public CategoryCountryOffers(Category category, Country country, LocalDate date, int indeed) {
        this.category = category;
        this.country = country;
        this.date = date;
        this.indeed = indeed;
    }
}
