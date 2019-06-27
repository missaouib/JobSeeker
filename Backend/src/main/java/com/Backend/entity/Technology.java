package com.Backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Data
@Entity
public class Technology {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    String type;

    public Technology(String name) {
        this.name = name;
    }
}
