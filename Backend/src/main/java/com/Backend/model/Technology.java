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
    int pracujplJobOffers;
    int linkedinJobOffers;
    int nofluffjobsJobOffers;
}
