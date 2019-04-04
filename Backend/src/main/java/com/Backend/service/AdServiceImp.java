package com.Backend.service;

import com.Backend.model.City;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdServiceImp implements AdService {

    private List<City> initCities() {
        return new ArrayList<>(
                Arrays.asList(
                        new City("Warszawa", 1764615),
                        new City("Krakow", 767348),
                        new City("Lodz", 690422),
                        new City("Wroclaw", 638586),
                        new City("Poznan", 538633),
                        new City("Gdansk", 464254),
                        new City("Szczecin", 403883),
                        new City("Bydgoszcz", 352313),
                        new City("Lublin", 339850),
                        new City("Bialystok", 297288)
                )
        );
    }

    public List<City> getAdsinCities(ModelMap technology) {
        List<City> cities = initCities();

        cities.forEach(city -> {
                    WebClient client = WebClient.create("https://www.pracuj.pl/praca/" + technology.get("technology") + ";kw/" + city.getName().toLowerCase() + ";wp");

                    Mono<ClientResponse> result = client.get()
                            .accept(MediaType.TEXT_PLAIN)
                            .exchange();

                    String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

                    // Can be changed in future by site owner
                    String htmlFirstTag = "<span class=\"results-header__offer-count-text-number\">";
                    String htmlLastTag = "</span> ofert";

                    city.setAdAmount(Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag))));
                    city.setAdPerCapita((double)Math.round((city.getAdAmount() * 1.0 / (city.getPopulation() / 100000)) * 100 ) / 100);
                }
        );

        return cities;
    }
}
