package com.Backend.infrastructure.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiagramPersistenceDto {
    private String name;
    private LocalDate date;
    private int linkedin;
    private int pracuj;
    private int indeed;
    private int noFluffJobs;
    private int justJoinIt;
    private int selectedOffers;

    public DiagramPersistenceDto() {
    }

    public DiagramPersistenceDto(String name, LocalDate date, int linkedin, int pracuj, int indeed, int noFluffJobs, int justJoinIt) {
        this.name = name;
        this.date = date;
        this.linkedin = linkedin;
        this.pracuj = pracuj;
        this.indeed = indeed;
        this.noFluffJobs = noFluffJobs;
        this.justJoinIt = justJoinIt;
    }

    public void addToOffers(int offers){
        this.selectedOffers += offers;
    }
}
