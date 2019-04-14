package com.Backend.service;

import com.Backend.model.City;
import com.Backend.model.Technology;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    private List<City> initCities() {
        return List.of(
                        new City("Warszawa", 1764615, 517.24),
                        new City("Krakow", 767348, 326.85),
                        new City("Lodz", 690422, 293.25),
                        new City("Wroclaw", 638586, 292.82),
                        new City("Poznan", 538633, 261.91),
                        new City("Gdansk", 464254, 261.96),
                        new City("Szczecin", 403883, 300.60),
                        new City("Bydgoszcz", 352313, 175.98),
                        new City("Lublin", 339850, 147.5),
                        new City("Bialystok", 297288, 102.12)
        );
    }

    private List<Technology> initTechnologies(){
        return List.of(
                        new Technology("Java", "Language"),
                        new Technology("Javascript", "Language"),
                        new Technology("Typescript", "Language"),
                        new Technology("c%23", "language"),
                        new Technology("Python", "Language"),
                        new Technology("Php", "Language"),
                        new Technology("c-x47-c%2b%2b", "Language"),
                        new Technology("Ruby", "Language"),
                        new Technology("Kotlin", "Language"),
                        new Technology("Swift", "Language"),

                        new Technology("Spring", "Framework"),
                        new Technology("Android", "Framework"),
                        new Technology("Angular", "Framework"),
                        new Technology("ReactJS", "Framework"),
                        new Technology("Vue.js", "Framework"),
                        new Technology("Node.js", "Framework"),
                        new Technology("Symfony", "Framework"),
                        new Technology("Laravel", "Framework"),
                        new Technology("iOS", "Framework"),
                        new Technology("Asp.net", "Framework"),

                        new Technology("SQL", "DevOps"),
                        new Technology("Linux", "DevOps"),
                        new Technology("Git", "DevOps"),
                        new Technology("Docker", "DevOps"),
                        new Technology("Jenkins", "DevOps"),
                        new Technology("Kubernetes", "DevOps"),
                        new Technology("Aws", "DevOps"),
                        new Technology("Maven", "DevOps"),
                        new Technology("Gradle", "DevOps"),
                        new Technology("JUnit", "DevOps")
                        // REST, Agile
        );
    }

    public List<City> getCities(ModelMap technology) {
        List<City> cities = initCities();

        if(technology.get("technology").toString().toLowerCase().equals("c#")){
            technology.replace("technology", "c%23");
        }

        if(technology.get("technology").toString().toLowerCase().equals("c/c++")){
            technology.replace("technology", "c-x47-c%2b%2b");
        }

        cities.forEach(city -> {

            WebClient url = WebClient.create("https://www.pracuj.pl/praca/" + technology.get("technology") + ";kw/" + city.getName().toLowerCase() + ";wp");

            if(technology.get("technology").toString().toLowerCase().equals("all")){
                url = WebClient.create("https://www.pracuj.pl/praca/" + city.getName().toLowerCase() + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
            }

            city.setJobAmount(getJobAmount(url));

            city.setJobOfferPer100kCitizens((double)Math.round((city.getJobAmount() * 1.0 / (city.getPopulation() * 1.0 / 100000)) * 100 ) / 100);
                    city.setDestinyOfPopulation((int)Math.round(city.getPopulation() / city.getAreaSquareKilometers()));
            }
        );

        return cities;
    }

    @Override
    public List<Technology> getTechnologies(ModelMap city) {
        List<Technology> technologies = initTechnologies();

        technologies.forEach(technology -> {

            WebClient url = WebClient.create("https://www.pracuj.pl/praca/" + technology.getName().toLowerCase() + ";kw/" + city.get("city") + ";wp");

            if((city.get("city").toString().toLowerCase()).equals("polska")){
                url = WebClient.create("https://www.pracuj.pl/praca/" + technology.getName().toLowerCase() + ";kw");
            }

            technology.setJobOffersAmount(getJobAmount(url));

            if(technology.getName().equals("c%23")){
                technology.setName("C#");
            }
            if(technology.getName().equals("c-x47-c%2b%2b")){
                technology.setName("C/C++");
            }
        });

        return technologies;
    }

    private int getJobAmount(WebClient url){

        int jobAmount = 0;

        Mono<ClientResponse> result = url.get()
                .accept(MediaType.TEXT_PLAIN)
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "<span class=\"results-header__offer-count-text-number\">";
        String htmlLastTag = "</span> ofert";

        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)){
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)));
        }

        return jobAmount;
    }
}
