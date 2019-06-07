package com.Backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Category {

    public Category(int pracujId, String polishName, String englishName) {
        this.pracujId = pracujId;
        this.polishName = polishName;
        this.englishName = englishName;
    }

    @Id
    @GeneratedValue
    Long id;

    int pracujId;
    String polishName;
    String englishName;
    int pracujOffers;
}
