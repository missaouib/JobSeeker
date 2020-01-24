package com.Backend.infrastructure.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiagramPersistenceDto {
    private String name;
    private LocalDate date;
    private int offers;

    public DiagramPersistenceDto() {
    }

    public DiagramPersistenceDto(String name, LocalDate date, int offers) {
        this.name = name;
        this.date = date;
        this.offers = offers;
    }
}
