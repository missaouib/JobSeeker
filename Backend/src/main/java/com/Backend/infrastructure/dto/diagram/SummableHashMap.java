package com.Backend.infrastructure.dto.diagram;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SummableHashMap extends HashMap<LocalDate, Integer> {

    public static Map<LocalDate, Integer> createMapWithoutDuplicates(List<Series> seriesWithDuplicateValues) {
        Map<LocalDate, Integer> map = new HashMap<>();
        seriesWithDuplicateValues.forEach(serie -> addToMap(map, serie));
        return map;
    }

    private static void addToMap(Map<LocalDate, Integer> map, Series serie) {
        if (map.containsKey(serie.getName())) {
            map.put(serie.getName(), map.get(serie.getName()) + serie.getValue());
        } else {
            map.put(serie.getName(), serie.getValue());
        }
    }
}
