package com.Backend.infrastructure.dto;

import lombok.Data;

@Data
public class JobsOffersInPolandDto {
    private String name;
    private int population;
    private double area;
    private int density;
    private int linkedin;
    private int indeed;
    private int pracuj;
    private int noFluffJobs;
    private int justJoinIt;
    private int total;
    private double per100k;
}
