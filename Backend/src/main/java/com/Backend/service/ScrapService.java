package com.Backend.service;

import com.Backend.domain.JustJoin;
import com.Backend.domain.NoFluffJobsList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Objects;

@Service
public class ScrapService {

    public int getLinkedinOffers(String url) {

        WebClient linkedinURL = WebClient.create(url);

        Mono<ClientResponse> result = linkedinURL.get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        return getHtmlSubstring(resultString, "Past Month <span class=\"filter-list__label-count\">(",
                ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>");
    }

    public int getIndeedOffers(String url) throws IOException {

        Document htmlDoc = Jsoup.connect(url).get();
        Elements htmlDiv = htmlDoc.select("div#searchCountPages");
        String div = htmlDiv.text();

        div = div
                .replaceAll(",", "")
                .replaceAll("\\.", "")
                .replaceAll("'", "")
                .replaceAll("\\s+", "");

        div = div.replaceAll("[^0-9]+", ",");

        if(div.charAt(0) == ',') {
            div = div.substring(1, div.length() - 1);
        } else {
            div = div.substring(0, div.length() - 1);
        }

        String[] numbers = div.split("\\s*,\\s*");

        if(Integer.parseInt(numbers[0]) == 0 || Integer.parseInt(numbers[1]) == 0){
            return 0;
        } else {
            return Math.max(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        }
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

        //https://nofluffjobs.com/api/posting

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
