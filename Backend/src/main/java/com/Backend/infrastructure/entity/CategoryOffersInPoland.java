package com.Backend.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CategoryOffersInPoland {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Category category;

    @ManyToOne
    private City city;

    private LocalDate date;
    private int pracuj;
    private int indeed;

    public CategoryOffersInPoland(Category category, City city, LocalDate date, int pracuj, int indeed) {
        this.category = category;
        this.city = city;
        this.date = date;
        this.pracuj = pracuj;
        this.indeed = indeed;
    }
}
