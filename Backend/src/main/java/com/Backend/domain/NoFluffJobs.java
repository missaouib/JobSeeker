package com.Backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class NoFluffJobs {
    String title;
    String technology;
    List<String> cities;
    List<String> seniority;

    @SuppressWarnings("unchecked")
    @JsonProperty("location")
    private void unpackNested(Map<String, Object> location) {
        List<Map<String,String>> places = (List<Map<String, String>>) location.get("places");
        this.cities = places.stream().map(x -> x.get("city")).collect(Collectors.toList());
    }
}