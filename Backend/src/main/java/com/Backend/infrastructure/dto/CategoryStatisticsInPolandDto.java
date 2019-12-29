package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class CategoryStatisticsInPolandDto {
    private String polishName;
    private String englishName;
    private int pracuj;
    private int indeed;
}
