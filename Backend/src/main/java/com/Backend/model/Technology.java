package com.Backend.model;

import lombok.Data;

@Data
public class Technology {

    public Technology(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String name;
    String type;
    int linkedinOffers;
    int pracujOffers;
    int noFluffJobsOffers;
    int justJoinOffers;
    int totalJobOffers;
}
