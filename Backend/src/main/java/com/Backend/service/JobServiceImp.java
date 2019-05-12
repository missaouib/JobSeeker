package com.Backend.service;

import com.Backend.model.City;
import com.Backend.model.Technology;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    private List<City> initCities() {
        return List.of(
                        new City("Warszawa", 1764615, 517.24, 9300),
                        new City("Krakow", 767348, 326.85, 9200),
                        new City("Lodz", 690422, 293.25, 5200),
                        new City("Wroclaw", 638586, 292.82, 8400),
                        new City("Poznan", 538633, 261.91, 7600),
                        new City("Gdansk", 464254, 261.96, 8600),
                        new City("Szczecin", 403883, 300.60, 5800),
                        new City("Bydgoszcz", 352313, 175.98, 5400),
                        new City("Lublin", 339850, 147.5, 6070),
                        new City("Bialystok", 297288, 102.12, 5860),
                        new City("Katowice", 296262, 164.64, 5460),
                        new City("Rzeszow", 193631, 126.57, 6100),
                        new City("Kielce", 195774, 109.45, 5000),
                        new City("Olsztyn", 173125, 88.33, 5350),
                        new City("Zielona Gora", 140113, 278.32, 5000),
                        new City("Opole", 128140, 148.99, 5340)
        );
    }

    private List<Technology> initTechnologies(){
        return List.of(
                        new Technology("Java", "Language"),
                        new Technology("Javascript", "Language"),
                        new Technology("Typescript", "Language"),
                        new Technology(".NET", "language"),
                        new Technology("Python", "Language"),
                        new Technology("PHP", "Language"),
                        new Technology("c%2B%2B", "Language"),
                        new Technology("Ruby", "Language"),
                        new Technology("Kotlin", "Language"),
                        new Technology("Scala", "Language"),
                        new Technology("Groovy", "Language"),
                        new Technology("Swift", "Language"),
                        new Technology("Objective-C", "Language"),
                        new Technology("Visual Basic", "Language"),

                        new Technology("Spring", "Framework"),
                        new Technology("JavaEE", "Framework"),
                        new Technology("Android", "Framework"),
                        new Technology("Angular", "Framework"),
                        new Technology("ReactJS", "Framework"),
                        new Technology("Vue.js", "Framework"),
                        new Technology("Node.js", "Framework"),
                        new Technology("JQuery", "Framework"),
                        new Technology("Symfony", "Framework"),
                        new Technology("Laravel", "Framework"),
                        new Technology("iOS", "Framework"),
                        new Technology("Asp.net", "Framework"),
                        new Technology("Django", "Framework"),
                        new Technology("Unity", "Framework"),

                        new Technology("SQL", "DevOps"),
                        new Technology("Linux", "DevOps"),
                        new Technology("Git", "DevOps"),
                        new Technology("Docker", "DevOps"),
                        new Technology("Jenkins", "DevOps"),
                        new Technology("Kubernetes", "DevOps"),
                        new Technology("AWS", "DevOps"),
                        new Technology("Azure", "DevOps"),
                        new Technology("HTML", "DevOps"),
                        new Technology("Maven", "DevOps"),
                        new Technology("Gradle", "DevOps"),
                        new Technology("JUnit", "DevOps"),
                        new Technology("Jira", "DevOps"),
                        new Technology("Scrum", "DevOps")
        );
    }

    public List<City> getItJobOffers(ModelMap technology) {
        List<City> cities = initCities();

        if(technology.get("technology").toString().toLowerCase().equals("c#")){
            technology.replace("technology", "c%23");
        }
        else if (technology.get("technology").toString().toLowerCase().equals("c++")){
            technology.replace("technology", "c%2b%2b");
        }

        cities.forEach(city -> {

            WebClient linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=" + technology.get("technology").toString().toLowerCase() + "&location=" + city.getName().toLowerCase());
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + technology.get("technology").toString().toLowerCase() + ";kw/" + city.getName().toLowerCase() + ";wp");

            if(technology.get("technology").toString().toLowerCase().equals("it category")){
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + city.getName().toLowerCase() + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?location=" + city.getName().toLowerCase() + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
            }

            city.setLinkedinJobOffers(getLinkedinOffers(linkedinURL));
            city.setPracujplJobOffers(getPracujplOffers(pracujURL));

            city.setJobOfferPer100kCitizens((double)Math.round(((city.getPracujplJobOffers() + city.getLinkedinJobOffers()) / 2 * 1.0 / (city.getPopulation() * 1.0 / 100000)) * 100 ) / 100);
            city.setDestinyOfPopulation((int)Math.round(city.getPopulation() / city.getAreaSquareKilometers()));
            }
        );

        return cities;
    }

    @Override
    public List<Technology> getTechnologyStatistics(ModelMap city) {
        List<Technology> technologies = initTechnologies();

        technologies.forEach(technology -> {

            WebClient linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=" + technology.getName().toLowerCase() + "&location=" + city.get("city").toString().toLowerCase());
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + technology.getName().toLowerCase() + ";kw/" + city.get("city").toString().toLowerCase() + ";wp");

            if((city.get("city").toString().toLowerCase()).equals("poland")){
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + technology.getName().toLowerCase() + ";kw");
            }

            technology.setLinkedinJobOffers(getLinkedinOffers(linkedinURL));
            technology.setPracujplJobOffers(getPracujplOffers(pracujURL));

            if(technology.getName().equals("c%2B%2B")){
                technology.setName("C++");
            }
        });

        return technologies;
    }

    private int getLinkedinOffers(WebClient url){

        Mono<ClientResponse> result = url.get()
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "Ostatni miesiąc <span class=\"filter-list__label-count\">(";
        String htmlLastTag = ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)){
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)).replaceAll(",", ""));
        }

        return jobAmount;
    }

    private int getPracujplOffers(WebClient url){

        Mono<ClientResponse> result = url.get()
                .accept(MediaType.TEXT_PLAIN)
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "<span class=\"results-header__offer-count-text-number\">";
        String htmlLastTag = "</span> ofert";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)){
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)));
        }

        return jobAmount;
    }
}
