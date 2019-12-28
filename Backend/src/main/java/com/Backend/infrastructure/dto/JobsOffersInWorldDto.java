package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class JobsOffersInWorldDto {
    String name;
    int population;
    double area;
    int density;
    int linkedin;
    int indeed;
    double per100k;
}
