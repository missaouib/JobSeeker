package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.domain.NoFluffJobsList;
import com.Backend.service.ScrapJobService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ScrapJobServiceImp implements ScrapJobService {

    public int getLinkedinOffers(String url) throws MalformedURLException {

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("headless");
        WebDriver webDriver = new RemoteWebDriver(new URL("http:/localhost:4444/wd/hub"), options);

        webDriver.get(url);
        String linkedin = null;
        try {
            String linkedin1 = webDriver.findElement(By.className("filter-list__label-count")).getText();
            System.out.println("what: " + linkedin1);
            //linkedin = linkedin1.substring(linkedin1.indexOf("(")+1, linkedin1.indexOf(")"));
        } catch(Exception e){
            linkedin = "0";
        }
        webDriver.quit();
        System.out.println(linkedin);
        return Integer.parseInt(linkedin);

//        WebClient linkedinURL = WebClient.create(url);
//
//        Mono<ClientResponse> result = linkedinURL.get()
//                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
//                .exchange();
//
//        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();
//
//        String htmlFirstTag = "Past Month <span class=\"filter-list__label-count\">(";
//        String htmlLastTag = ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>";
//
//        int jobAmount = 0;
//        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
//            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)).replaceAll(",", ""));
//        }
//
//        return jobAmount;
    }

    public int getPracujOffers(String url) {

        WebClient pracujURL = WebClient.create(url);

        Mono<ClientResponse> result = pracujURL
                .get()
                .header("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion")
                .accept(MediaType.TEXT_PLAIN)
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        String htmlFirstTag = "<span class=\"results-header__offer-count-text-number\">";
        String htmlLastTag = "</span> ofert";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)));
        }

        return jobAmount;
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
}
