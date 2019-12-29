package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class TechnologyStatisticsInWorldDto {
    private String name;
    private String type;
    private int linkedin;
    private int indeed;
    private int total;
}
