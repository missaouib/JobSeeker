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
public class JobServiceImp implements JobService {

    private List<City> initCities() {
        return new ArrayList<>(
                Arrays.asList(
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
                )
        );
    }

    public List<City> getJobs(ModelMap technology) {
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

                    if (resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)){
                        city.setJobAmount(Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag))));
                    } else {
                        city.setJobAmount(0);
                    }

                    city.setJobOfferPer100kCitizens((double)Math.round((city.getJobAmount() * 1.0 / (city.getPopulation() / 100000)) * 100 ) / 100);
                    city.setDestinyOfPopulation((int)Math.round(city.getPopulation() / city.getAreaSquareKilometers()));
                }
        );

        return cities;
    }
}
