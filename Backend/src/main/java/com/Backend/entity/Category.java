package com.Backend.entity;

import lombok.Data;

import javax.persistence.Column;
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

    @Column(unique = true)
    String englishName;
    int pracujId;
    String polishName;
}
