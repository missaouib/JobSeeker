package com.Backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NoFluffJobsDto {
    String title;
    String city;
    List<String> seniority;

    @SuppressWarnings("unchecked")
    @JsonProperty("location")
    private void unpackNested(Map<String, Object> location) {
        List<Map<String,String>> places = (List<Map<String, String>>) location.get("places");
        this.city = places.get(0).get("city");
    }
}
