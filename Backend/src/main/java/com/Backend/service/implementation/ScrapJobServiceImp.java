package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.domain.NoFluffJobsList;
import com.Backend.service.ScrapJobService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;

@Service
public class ScrapJobServiceImp implements ScrapJobService {

    public int getLinkedinOffers(String url) {

        WebClient linkedinURL = WebClient.create(url);

        Mono<ClientResponse> result = linkedinURL.get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        return getHtmlSubstring(resultString, "Past Month <span class=\"filter-list__label-count\">(",
                ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>");
    }

    public int getIndeedOffers(String url) {
        WebClient indeedURL = WebClient.create(url);

        Mono<ClientResponse> result = indeedURL.get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        return getHtmlSubstring(resultString, "Strona 1 - ", " ofert<");
    }

    public int getPracujOffers(String url) {

        WebClient pracujURL = WebClient.create(url);

        Mono<ClientResponse> result = pracujURL.get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        return getHtmlSubstring(resultString, "<span class=\"results-header__offer-count-text-number\">", "</span> ofert");
    }

    public int getNoFluffJobsOffers(String url) {

        WebClient noFluffJobsURL = WebClient.create(url);

        NoFluffJobsList postings =  noFluffJobsURL
                .get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(NoFluffJobsList.class)
                .block();

        return Objects.requireNonNull(postings).getPostings().size();
    }

    public List<JustJoin> getJustJoin() {

        WebClient justJoinURL = WebClient.create("https://justjoin.it/api/offers");

        return justJoinURL.get()
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToFlux(JustJoin.class)
                .collectList()
                .block();
    }

    public String removePolishSigns(String city){
        return Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("ł", "l");
    }

    private int getHtmlSubstring(String resultString, String htmlFirstPart, String htmlSecondPart){
        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstPart) && resultString.contains(htmlSecondPart))
            jobAmount = Integer.parseInt(resultString
                    .substring(resultString.indexOf(htmlFirstPart) + htmlFirstPart.length(), resultString.indexOf(htmlSecondPart))
                    .replaceAll(",", "")
                    .replaceAll("[^\\x00-\\x7F]", ""));

        return jobAmount;
    }
}
