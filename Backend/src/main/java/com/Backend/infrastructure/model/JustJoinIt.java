package com.Backend.infrastructure.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JustJoinIt {
    private String title;
    private String city;
    private List<Map<String, String>> skills;
}
