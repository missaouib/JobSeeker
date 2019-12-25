package com.Backend.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
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
