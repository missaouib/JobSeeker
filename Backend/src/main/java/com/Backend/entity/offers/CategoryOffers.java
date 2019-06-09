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

    public CategoryOffers(Category category, LocalDate date, int pracuj) {
        this.category = category;
        this.date = date;
        this.pracuj = pracuj;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private City city;

    private LocalDate date;

    private int pracuj;
}
