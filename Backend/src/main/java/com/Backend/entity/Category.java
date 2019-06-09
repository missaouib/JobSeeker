package com.Backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    int pracujId;
    String polishName;
    String englishName;
}