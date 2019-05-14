package com.Backend.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JustJoinDto {
    String title;
    String city;
    List<Map<String, String>> skills;
    //int salary_from;
    //int salary_to;
    //String experience_level;
}
