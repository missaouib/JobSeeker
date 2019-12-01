package com.Backend.dto;

import lombok.Data;

@Data
public class CountryDto {
    Long id;
    String name;
    int population;
    double area;
    int density;
    int linkedin;
    int indeed;
    double per100k;
}
