package com.Backend.infrastructure.dto.diagram;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiagramPersistenceDto {
    private String name;
    private LocalDate date;
    private int linkedin;
    private int indeed;
    private int pracuj;
    private int noFluffJobs;
    private int justJoinIt;
    private int selectedOffers;

    public DiagramPersistenceDto() {
    }

    public DiagramPersistenceDto(String name, LocalDate date, int linkedin, int indeed) {
        this.name = name;
        this.date = date;
        this.linkedin = linkedin;
        this.indeed = indeed;
    }

    public DiagramPersistenceDto(String name, LocalDate date, int linkedin, int indeed, int pracuj, int noFluffJobs, int justJoinIt) {
        this.name = name;
        this.date = date;
        this.linkedin = linkedin;
        this.indeed = indeed;
        this.pracuj = pracuj;
        this.noFluffJobs = noFluffJobs;
        this.justJoinIt = justJoinIt;
    }

    public void addToOffers(int offers){
        this.selectedOffers += offers;
    }
}
