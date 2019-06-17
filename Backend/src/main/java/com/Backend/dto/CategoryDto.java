package com.Backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CategoryDto {
    Long id;
    LocalDate date;
    String polishName;
    String englishName;
    int pracuj;
}
