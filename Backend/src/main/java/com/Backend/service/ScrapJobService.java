package com.Backend.service;

import com.Backend.domain.JustJoin;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public interface ScrapJobService {
    List<Country> getItJobOffersInWorld(ModelMap technology);
    List<Technology> getTechnologyStatistics(ModelMap city);

    int getLinkedinOffers(WebClient url);
    int getPracujOffers(WebClient url);
    int getNoFluffJobsOffers(WebClient url);
    List<JustJoin> getJustJoin();
    String removePolishSigns(String city);

}
