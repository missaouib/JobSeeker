package com.Backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CityDto {
    LocalDate date;
    String name;
    int population;
    double area;
    int density;
    int linkedin;
    int pracuj;
    int nofluffJobs;
    int justJoin;
    int total;
    double jobOfferPer100kCitizens;
}
