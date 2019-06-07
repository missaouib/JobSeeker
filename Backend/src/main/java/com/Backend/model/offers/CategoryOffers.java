package com.Backend.model.offers;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CategoryOffers {

    int idCategory;
    LocalDate date;
    int pracuj;
}
