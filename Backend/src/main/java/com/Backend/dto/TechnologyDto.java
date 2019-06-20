package com.Backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TechnologyDto {
    Long id;
    LocalDate date;
    String name;
    String type;
    int linkedin;
    int pracuj;
    int noFluffJobs;
    int justJoin;
    int total;
}
