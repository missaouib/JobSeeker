package com.Backend.service;

import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyCityOffers;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.model.NoFluffJobs;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RequestCreator {

    public static final String USER_AGENT = "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion";

    public int scrapLinkedinOffers(String url) {

        WebClient linkedinURL = WebClient.create(url);

        Mono<ClientResponse> response = linkedinURL.get()
                .header("User-Agent", USER_AGENT)
                .exchange();

        String responseString = response.flatMap(res -> res.bodyToMono(String.class)).block();

        return getHtmlSubstring(responseString, "Past Month <span class=\"filter-list__label-count\">(",
                ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>");
    }

    public int scrapIndeedOffers(String url) throws IOException {

        Document htmlDoc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .referrer("http://www.google.com")
                .followRedirects(true)
                .get();

        Elements responseHtmlDiv = htmlDoc.select("div#searchCountPages");
        String div = responseHtmlDiv.text();

        div = div
                .replaceAll(",", "")
                .replaceAll("\\.", "")
                .replaceAll("'", "")
                .replaceAll("\\s+", "");

        div = div.replaceAll("[^0-9]+", ",");

        try {
            if (div.charAt(0) == ',') {
                div = div.substring(1, div.length() - 1);
            } else {
                div = div.substring(0, div.length() - 1);
            }

            String[] numbers = div.split("\\s*,\\s*");

            if (Integer.parseInt(numbers[0]) == 0 || Integer.parseInt(numbers[1]) == 0) {
                return 0;
            } else {
                return Math.max(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public int scrapPracujOffers(String url) {

        WebClient pracujURL = WebClient.create(url);

        Mono<ClientResponse> response = pracujURL.get()
                .header("User-Agent", USER_AGENT)
                .exchange();

        String responseString = response.flatMap(res -> res.bodyToMono(String.class)).block();

        return getHtmlSubstring(responseString, "<span class=\"results-header__offer-count-text-number\">", "</span> ofert");
    }

    public int scrapNoFluffJobsOffers(String url) {

        WebClient noFluffJobsURL = WebClient.create(url);

        NoFluffJobs response = noFluffJobsURL
                .get()
                .header("User-Agent", USER_AGENT)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(NoFluffJobs.class)
                .block();

        return Objects.requireNonNull(response).getPostings().size();
    }

    public List<JustJoinIt> scrapJustJoinIT() {

        WebClient justJoinITUrl = WebClient.create(UrlBuilder.justJoinItBuildUrlForCity());

        return justJoinITUrl.get()
                .header("User-Agent", USER_AGENT)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToFlux(JustJoinIt.class)
                .collectList()
                .block();
    }

    public String removePolishSigns(String city) {
        return Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("ł", "l");
    }

    public int extractJustJoinItJson(List<JustJoinIt> offers, City city, Technology technology, String technologyNameUpperCase) {

        TechnologyCityOffers technologyCityOffers = new TechnologyCityOffers(city, technology, LocalDate.now());

        String cityNameASCII = removePolishSigns(city.getName()).toLowerCase();
        String cityNameUTF8 = city.getName().toLowerCase();
        String technologyName = technologyNameUpperCase.toLowerCase();

        if (cityNameASCII.equals("poland")) {
            technologyCityOffers.setJustJoinIT((int) offers
                    .stream()
                    .filter(filterTechnology -> {
                        if (technologyName.equals("all jobs") || technologyName.equals("all it jobs")) {
                            return true;
                        } else {
                            return filterTechnology.getTitle().toLowerCase().contains(technologyName)
                                    || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(technologyName);
                        }
                    })
                    .count());
        } else {
            technologyCityOffers.setJustJoinIT((int) offers
                    .stream()
                    .filter(filterTechnology -> {
                        if (technologyName.equals("all jobs") || technologyName.equals("all it jobs")) {
                            return true;
                        } else {
                            return filterTechnology.getTitle().toLowerCase().contains(technologyName)
                                    || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(technologyName);
                        }
                    })
                    .filter(filterCity -> {
                        if (cityNameASCII.equals("warszawa")) {
                            return (filterCity.getCity().toLowerCase().contains(cityNameUTF8) || filterCity.getCity().toLowerCase().contains(cityNameASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                        } else if (cityNameUTF8.equals("kraków")) {
                            return (filterCity.getCity().toLowerCase().contains(cityNameUTF8) || filterCity.getCity().toLowerCase().contains(cityNameASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                        } else {
                            return (filterCity.getCity().toLowerCase().contains(cityNameUTF8) || filterCity.getCity().toLowerCase().contains(cityNameASCII));
                        }
                    })
                    .count());
        }
            return technologyCityOffers.getJustJoinIT();
    }

    private int getHtmlSubstring(String resultString, String htmlFirstPart, String htmlSecondPart) {
        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstPart) && resultString.contains(htmlSecondPart))
            jobAmount = Integer.parseInt(resultString
                    .substring(resultString.indexOf(htmlFirstPart) + htmlFirstPart.length(), resultString.indexOf(htmlSecondPart))
                    .replaceAll(",", "")
                    .replaceAll("[^\\x00-\\x7F]", ""));

        return jobAmount;
    }
}
