package com.Backend.model.offers;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CityOffers {

    int idCity;
    LocalDate date;
    int linkedin;
    int pracuj;
    int nofluffJobs;
    int justJoin;
    int total;
}
