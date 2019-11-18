package com.Backend.service;

import com.Backend.domain.JustJoin;

import java.util.List;

public interface ScrapJobService {
    int getLinkedinOffers(String url);
    int getIndeedOffers(String url);
    int getPracujOffers(String url);
    int getNoFluffJobsOffers(String url);
    List<JustJoin> getJustJoin();
    String removePolishSigns(String city);

}
