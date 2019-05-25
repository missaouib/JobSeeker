package com.Backend.model;

import lombok.Data;

@Data
public class Category {

    public Category(int id, String polishName, String englishName) {
        this.id = id;
        this.polishName = polishName;
        this.englishName = englishName;
    }

    int id;
    String polishName;
    String englishName;
    int pracujOffers;
}
