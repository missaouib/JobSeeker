package com.Backend.entity.offers;

import com.Backend.entity.Category;
import com.Backend.entity.City;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class CategoryOffers {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private City city;

    private LocalDate date;
    private int pracuj;

    public CategoryOffers(Category category, City city, LocalDate date, int pracuj) {
        this.category = category;
        this.city = city;
        this.date = date;
        this.pracuj = pracuj;
    }
}
