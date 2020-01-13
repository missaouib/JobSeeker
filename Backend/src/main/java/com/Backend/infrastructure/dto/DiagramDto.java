package com.Backend.infrastructure.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DiagramDto {
    private String name;
    private List<Series> series;

    private static class Series {
        private LocalDate name;
        private int value;
    }

}
