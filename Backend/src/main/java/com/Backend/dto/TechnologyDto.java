package com.Backend.dto;

import lombok.Data;

@Data
public class TechnologyDto {
    Long id;
    String name;
    String type;
    int linkedin;
    int indeed;
    int pracuj;
    int noFluffJobs;
    int justJoin;
    int total;
}
