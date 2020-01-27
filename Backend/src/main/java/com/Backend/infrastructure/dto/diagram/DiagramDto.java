package com.Backend.infrastructure.dto.diagram;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class DiagramDto implements Comparable<DiagramDto> {

    private String name;
    private List<Series> series;

    public DiagramDto(String name, List<Series> series) {
        this.name = name;
        this.series = getDistinctSeriesWithSumValues(series);
    }

    @Override
    public int compareTo(DiagramDto diagramDto) {
        return Integer.compare(series.stream().mapToInt(Series::getValue).sum(),
                diagramDto.getSeries().stream().mapToInt(Series::getValue).sum()) * -1;
    }

    private List<Series> getDistinctSeriesWithSumValues(List<Series> seriesWithDuplicateValues) {
        Map<LocalDate, Integer> map = SummableHashMap.createMapWithoutDuplicates(seriesWithDuplicateValues);

        return map.entrySet().stream()
                .map(entry -> new Series(entry.getKey(), entry.getValue()))
                .sorted()
                .collect(Collectors.toList());
    }


}
