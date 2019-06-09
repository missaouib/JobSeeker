package com.Backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    public City(String name) {
        this.name = name;
    }
}
