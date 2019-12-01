package com.Backend.service;

import com.Backend.domain.JustJoin;

import java.io.IOException;
import java.util.List;

public interface ScrapJobService {
    int getLinkedinOffers(String url);
    int getIndeedOffers(String url) throws IOException;
    int getPracujOffers(String url);
    int getNoFluffJobsOffers(String url);
    List<JustJoin> getJustJoin();
    String removePolishSigns(String city);

}
