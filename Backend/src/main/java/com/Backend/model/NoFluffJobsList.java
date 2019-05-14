package com.Backend.model;

import com.Backend.model.dto.NoFluffJobsDto;
import lombok.Data;

import java.util.List;

@Data
public class NoFluffJobsList {
    List<NoFluffJobsDto> postings;
}
