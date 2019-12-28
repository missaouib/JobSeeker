package com.Backend.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class NoFluffJobs {

    private List<NoFluffJob> postings;

    @Data
    public static class NoFluffJob {
        private String title;
        private String technology;
        private List<String> cities;
        private List<String> seniority;
        //location.get("fullyRemote")

        @SuppressWarnings("unchecked")
        @JsonProperty("location")
        private void unpackNested(Map<String, Object> location) {
            List<Map<String, String>> places = (List<Map<String, String>>) location.get("places");
            this.cities = places.stream().map(x -> x.get("city").toLowerCase()).collect(Collectors.toList());
        }
    }

}
