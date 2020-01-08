package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class TechnologyStatisticsInPolandDto {
    private String name;
    private String type;
    private int linkedin;
    private int indeed;
    private int pracuj;
    private int noFluffJobs;
    private int justJoinIt;
    private int total;
}
