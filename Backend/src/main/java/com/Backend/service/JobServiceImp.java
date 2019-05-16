package com.Backend.service;

import com.Backend.model.City;
import com.Backend.model.NoFluffJobsList;
import com.Backend.model.Technology;
import com.Backend.model.dto.JustJoinDto;
import com.Backend.model.dto.NoFluffJobsDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;

@Service
public class JobServiceImp implements JobService {

    private List<City> initCities() {
        return List.of(
                new City("Warszawa", 1764615, 517.24, 9300),
                new City("Kraków", 767348, 326.85, 9200),
                new City("Łódź", 690422, 293.25, 5200),
                new City("Wrocław", 638586, 292.82, 8400),
                new City("Poznań", 538633, 261.91, 7600),
                new City("Gdańsk", 464254, 261.96, 8600),
                new City("Szczecin", 403883, 300.60, 5800),
                new City("Bydgoszcz", 352313, 175.98, 5400),
                new City("Lublin", 339850, 147.5, 6070),
                new City("Białystok", 297288, 102.12, 5860),
                new City("Katowice", 296262, 164.64, 5460),
                new City("Rzeszów", 193631, 126.57, 6100),
                new City("Kielce", 195774, 109.45, 5000),
                new City("Olsztyn", 173125, 88.33, 5350),
                new City("Zielona Góra", 140113, 278.32, 5000),
                new City("Opole", 128140, 148.99, 5340)
        );
    }

    private List<Technology> initTechnologies() {
        return List.of(
                new Technology("Java", "Language"),
                new Technology("Javascript", "Language"),
                new Technology("Typescript", "Language"),
                new Technology(".NET", "language"),
                new Technology("Python", "Language"),
                new Technology("PHP", "Language"),
                new Technology("C++", "Language"),
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

        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<City> cities = initCities();
        List<NoFluffJobsDto> noFluffJobsDtoOffers = getNoFluffJobsOffers().get(0).getPostings();
        List<JustJoinDto> justJoinDtoOffers = getJustJoin();

        cities.forEach(city -> {

                    String selectedCityUTF8 = city.getName().toLowerCase();
                    String selectedCityASCII = removePolishSigns(selectedCityUTF8);
                    WebClient linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
                    WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");

                    switch(selectedTechnology){
                        case "it category":
                            linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                            break;
                        case "c++":
                            linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
                            break;
                        case "c#":
                            linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCityASCII);
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/c%23;kw/" + selectedCityASCII + ";wp");
                            break;
                    }


                    city.setLinkedinOffers(getLinkedinOffers(linkedinURL));
                    city.setPracujOffers(getPracujOffers(pracujURL));

                    if(selectedTechnology.equals("it category")){
                        city.setNoFluffJobsOffers((int)noFluffJobsDtoOffers
                                .stream()
                                .flatMap(filterCity -> filterCity.getCities().stream()
                                        .filter(x -> {
                                            if(selectedCityUTF8.equals("warszawa")){
                                                return (x.toLowerCase().contains(selectedCityUTF8) || x.toLowerCase().contains(selectedCityASCII) || x.toLowerCase().contains("warsaw"));
                                            } else if (selectedCityUTF8.equals("kraków")){
                                                return (x.toLowerCase().contains(selectedCityUTF8) || x.toLowerCase().contains(selectedCityASCII) || x.toLowerCase().contains("cracow"));
                                            } else {
                                                return (x.toLowerCase().contains(selectedCityUTF8) || x.toLowerCase().contains(selectedCityASCII));
                                            }
                                        }))
                                .count());

                        city.setJustJoinOffers((int)justJoinDtoOffers
                                .stream()
                                .filter(filterCity -> filterCity.getCity().toLowerCase().equals(selectedCityUTF8) || filterCity.getCity().toLowerCase().equals(selectedCityASCII))
                                .count());
                    } else {
                        city.setNoFluffJobsOffers((int)noFluffJobsDtoOffers
                                .stream()
                                .filter(x -> Objects.nonNull(x.getTechnology()))
                                .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology + " ")
                                        || filterTechnology.getTechnology().toLowerCase().contains(selectedTechnology))
                                .flatMap(filterCity -> filterCity.getCities().stream()
                                        .filter(x -> {
                                            if(selectedCityUTF8.equals("warszawa")){
                                                return (x.toLowerCase().contains(selectedCityUTF8) || x.toLowerCase().contains(selectedCityASCII) || x.toLowerCase().contains("warsaw"));
                                            } else if (selectedCityUTF8.equals("kraków")){
                                                return (x.toLowerCase().contains(selectedCityUTF8) || x.toLowerCase().contains(selectedCityASCII) || x.toLowerCase().contains("cracow"));
                                            } else {
                                                return (x.toLowerCase().contains(selectedCityUTF8) || x.toLowerCase().contains(selectedCityASCII));
                                            }
                                        }))
                                .count());

                        city.setJustJoinOffers((int)justJoinDtoOffers
                                .stream()
                                .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology))
                                .filter(filterCity -> filterCity.getCity().toLowerCase().equals(selectedCityUTF8) || filterCity.getCity().toLowerCase().equals(selectedCityASCII))
                                .count());
                    }

                    city.setJobOfferPer100kCitizens((double) Math.round(((city.getPracujOffers() + city.getLinkedinOffers() + city.getJustJoinOffers() + city.getNoFluffJobsOffers()) / 4.0 * 1.0 / (city.getPopulation() * 1.0 / 100000)) * 100) / 100);
                    city.setDestinyOfPopulation((int) Math.round(city.getPopulation() / city.getAreaSquareKilometers()));
                }
        );
        return cities;
    }

    @Override
    public List<Technology> getTechnologyStatistics(ModelMap city) {

        String selectedCityUTF8 = city.get("city").toString().toLowerCase();
        String selectedCityASCII = removePolishSigns(selectedCityUTF8);
        List<Technology> technologies = initTechnologies();
        List<NoFluffJobsDto> noFluffJobsDtoOffers = getNoFluffJobsOffers().get(0).getPostings();
        List<JustJoinDto> justJoinDtoOffers = getJustJoin();

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();
            WebClient linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityUTF8);
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityUTF8 + ";wp");

            if(selectedCityUTF8.equals("poland")) {
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw");
            }
            if(selectedTechnology.equals("c++")) {
                linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/c++-jobs-polska");
            }

            technology.setLinkedinOffers(getLinkedinOffers(linkedinURL));
            technology.setPracujOffers(getPracujOffers(pracujURL));

            if(selectedCityUTF8.equals("poland")){
                technology.setNoFluffJobsOffers((int)noFluffJobsDtoOffers
                        .stream()
                        .filter(x -> Objects.nonNull(x.getTechnology()))
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology + " ")
                                || filterTechnology.getTechnology().toLowerCase().contains(selectedTechnology))
                        .count());

                technology.setJustJoinOffers((int)justJoinDtoOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().get(0).containsValue((selectedTechnology)))
                        .count());
            } else {
                technology.setNoFluffJobsOffers((int)noFluffJobsDtoOffers
                        .stream()
                        .filter(x -> Objects.nonNull(x.getTechnology()))
                        //.filter(filterCity -> filterCity.getCities().)
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology + " ")
                                || filterTechnology.getTechnology().toLowerCase().contains(selectedTechnology))
                        .count());

                technology.setJustJoinOffers((int)justJoinDtoOffers
                        .stream()
                        .filter(filterCity -> filterCity.getCity().toLowerCase().equals(selectedCityUTF8) || filterCity.getCity().toLowerCase().equals(selectedCityASCII))
//                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
//                                || filterTechnology.getSkills().contains(selectedTechnology))
                        .count());
            }
        });
        return technologies;
    }

    private int getLinkedinOffers(WebClient url) {

        Mono<ClientResponse> result = url.get()
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "Ostatni miesiąc <span class=\"filter-list__label-count\">(";
        String htmlLastTag = ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)).replaceAll(",", ""));
        }

        return jobAmount;
    }

    private int getPracujOffers(WebClient url) {

        Mono<ClientResponse> result = url.get()
                .accept(MediaType.TEXT_PLAIN)
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "<span class=\"results-header__offer-count-text-number\">";
        String htmlLastTag = "</span> ofert";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)));
        }

        return jobAmount;
    }

    private List<NoFluffJobsList> getNoFluffJobsOffers() {

        WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/posting");

        return noFluffJobsURL
                .get()
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToFlux(NoFluffJobsList.class)
                .collectList()
                .block();
    }

    private List<JustJoinDto> getJustJoin() {

        WebClient justJoinURL = WebClient.create("https://justjoin.it/api/offers");

        return justJoinURL.get()
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToFlux(JustJoinDto.class)
                .collectList()
                .block();
    }

    private String removePolishSigns(String city){
        return Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("ł", "l");
    }
}
