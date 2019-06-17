package com.Backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CountryDto {
    Long id;
    LocalDate date;
    String name;
    int population;
    double area;
    int density;
    int linkedin;
}
