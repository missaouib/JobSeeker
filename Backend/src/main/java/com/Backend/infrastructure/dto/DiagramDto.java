package com.Backend.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class DiagramDto implements Comparable<DiagramDto> {
    private String name;
    private List<Series> series;

    @Data
    @NoArgsConstructor
    public static class Series {

        private LocalDate name;
        private int value;
        public Series(LocalDate name, int value) {
            this.name = name;
            this.value = value;
        }

    }

    @Override
    public int compareTo(DiagramDto diagramDto) {
        return Integer.compare(series.stream().mapToInt(DiagramDto.Series::getValue).sum(),
                diagramDto.getSeries().stream().mapToInt(DiagramDto.Series::getValue).sum()) * -1;
    }

}
