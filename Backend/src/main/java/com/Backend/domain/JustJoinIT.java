package com.Backend.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JustJoinIT {
    String title;
    String city;
    List<Map<String, String>> skills;
//    boolean isRemote;
//    String experience_level;
//    int salary_from;
//    int salary_to;
}
