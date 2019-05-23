package com.Backend.model;

import lombok.Data;

@Data
public class Category {

    public Category(String polishName, String englishName) {
        this.polishName = polishName;
        this.englishName = englishName;
    }

    String polishName;
    String englishName;
    int pracujOffers;
}
