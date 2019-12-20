package com.Backend.infrastructure.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class CategoryCityOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private City city;

    private LocalDate date;
    private int pracuj;

    public CategoryCityOffers(Category category, City city, LocalDate date, int pracuj) {
        this.category = category;
        this.city = city;
        this.date = date;
        this.pracuj = pracuj;
    }
}
