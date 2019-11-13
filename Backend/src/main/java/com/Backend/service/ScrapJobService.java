package com.Backend.service;

import com.Backend.domain.JustJoin;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.util.List;

public interface ScrapJobService {
    int getLinkedinOffers(String url);
    int getPracujOffers(String url);
    int getNoFluffJobsOffers(String url);
    List<JustJoin> getJustJoin();
    String removePolishSigns(String city);

}
