package com.Backend.infrastructure.dto;

import java.time.LocalDate;

public interface DiagramPersistenceDto {
    String getName();
    LocalDate getDate();
    int getOffers();
}
