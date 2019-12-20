package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class CategoryDto {
    Long id;
    String polishName;
    String englishName;
    int pracuj;
}
