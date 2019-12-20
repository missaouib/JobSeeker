package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class CityDto {
    Long id;
    String name;
    int population;
    double area;
    int density;
    int linkedin;
    int indeed;
    int pracuj;
    int noFluffJobs;
    int justJoinIT;
    int total;
    double per100k;
}
