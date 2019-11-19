package com.Backend.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JustJoin {
    String title;
    String city;
    List<Map<String, String>> skills;
}
