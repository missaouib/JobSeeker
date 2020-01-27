package com.Backend.infrastructure.dto.diagram;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Series implements Comparable<Series> {

    private LocalDate name;
    private int value;

    public Series(LocalDate name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int compareTo(Series series) {
        return name.compareTo(series.getName());
    }
}