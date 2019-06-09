package com.Backend.domain;

import lombok.Data;

import java.util.List;

@Data
public class NoFluffJobsList {
    List<NoFluffJobsDto> postings;
}
