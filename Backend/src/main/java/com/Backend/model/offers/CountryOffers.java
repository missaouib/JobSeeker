package com.Backend.model.offers;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CountryOffers {

    int idCountry;
    LocalDate date;
    int linkedin;
}
