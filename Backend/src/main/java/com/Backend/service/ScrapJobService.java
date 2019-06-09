package com.Backend.service;

import com.Backend.domain.JustJoin;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public interface ScrapJobService {
    int getLinkedinOffers(WebClient url);
    int getPracujOffers(WebClient url);
    int getNoFluffJobsOffers(WebClient url);
    List<JustJoin> getJustJoin();
    String removePolishSigns(String city);

}
