package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.domain.NoFluffJobsList;
import com.Backend.service.ScrapJobService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public int getIndeedOffers(String url) throws IOException {
        WebClient indeedURL = WebClient.create(url);

        Mono<ClientResponse> result = indeedURL.get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        System.out.println(resultString);

        Document document = Jsoup.connect(url).get();
        Elements info = document.select("div#Document document");

        resultString = info.toString();

        resultString
                .replaceAll(",", "")
                //.replaceAll(".", "")
                .replaceAll("[^\\x00-\\x7F]", "");

        Pattern p = Pattern.compile("[^\\d]*[\\d]+[^\\d]+([\\d]+)");
        Matcher m = p.matcher(resultString);

        System.out.println(resultString);

        if (m.find()) {
            System.out.println(m.group(1));
        }

        return Integer.parseInt(m.group(1));
        //return getHtmlSubstring(resultString, "Strona 1 - ", " ofert<");
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
