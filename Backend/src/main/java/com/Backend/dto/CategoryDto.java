package com.Backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CategoryDto {
    String polishName;
    String englishName;
    LocalDate date;
    int pracuj;
}
