package com.Backend.infrastructure.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    int population;
    double area;
    int density;
}
