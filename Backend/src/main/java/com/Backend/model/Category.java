package com.Backend.model;

import lombok.Data;

@Data
public class Category {

    public Category(int pracujId, String polishName, String englishName) {
        this.pracujId = pracujId;
        this.polishName = polishName;
        this.englishName = englishName;
    }

    int pracujId;
    String polishName;
    String englishName;
    int pracujOffers;
}
