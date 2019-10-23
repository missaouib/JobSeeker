package com.Backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CityDto {
    Long id;
    String name;
    int population;
    double area;
    int density;
    int linkedin;
    int pracuj;
    int noFluffJobs;
    int justJoin;
    int total;
    double per100k;
}
