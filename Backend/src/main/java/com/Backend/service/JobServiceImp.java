package com.Backend.service;

import com.Backend.model.Category;
import com.Backend.model.City;
import com.Backend.model.NoFluffJobsList;
import com.Backend.model.Technology;
import com.Backend.model.dto.CategoryDto;
import com.Backend.model.dto.JustJoinDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    private ModelMapper modelMapper;

    JobServiceImp(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

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
                new Technology("Java EE", "Framework"),
                new Technology("Android", "Framework"),
                new Technology("Angular", "Framework"),
                new Technology("React", "Framework"),
                new Technology("Vue", "Framework"),
                new Technology("Node", "Framework"),
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

    private List<Category> initCategories(){
        return List.of(
                new Category(5001, "Administracja biurowa","Office administration"),
                new Category(5037, "Doradztwo / Konsulting","Consulting"),
                new Category(5002, "Badania i rozwój","Research and development"),
                new Category(5003, "Bankowość","Banking"),
                new Category(5004, "BHP / Ochrona środowiska","Health and Safety / Environmental protection"),
                new Category(5005, "Budownictwo","Architecture"),
                new Category(5006, "Call Center","Call Center"),
                new Category(5007, "Edukacja / Szkolenia","Education / Training"),
                new Category(5008, "Finanse / Ekonomia","Finance / Economy"),
                new Category(5009, "Franczyza / Własny biznes","Own business"),
                new Category(5010, "Hotelarstwo / Gastronomia / Turystyka","Hospitality / Gastronomy / Tourism"),
                new Category(5011, "Human Resources / Zasoby ludzkie","Human Resources"),
                new Category(5013, "Internet / e-Commerce / Nowe media","Internet / e-Commerce / New media"),
                new Category(5014, "Inżynieria","Engineering"),
                new Category(5015, "IT - Administracja","IT - Administration"),
                new Category(5016, "IT - Rozwój oprogramowania","IT - Software development"),
                new Category(5017, "Łańcuch dostaw","Supply chain"),
                new Category(5018, "Marketing","Marketing"),
                new Category(5019, "Media / Sztuka / Rozrywka","Media / Arts / Entertainment"),
                new Category(5020, "Nieruchomości","Real estate"),
                new Category(5021, "Obsługa klienta","Customer service"),
                new Category(5022, "Praca fizyczna","Physical work"),
                new Category(5023, "Prawo","Law"),
                new Category(5024, "Produkcja","Production"),
                new Category(5025, "Public Relations","Public Relations"),
                new Category(5026, "Reklama / Grafika / Kreacja / Fotografia","Advertisement / Graphic / Photography"),
                new Category(5027, "Sektor publiczny","Public sector"),
                new Category(5028, "Sprzedaż","Sale"),
                new Category(5031, "Transport / Spedycja","Transport / Shipping"),
                new Category(5032, "Ubezpieczenia","Insurance"),
                new Category(5033, "Zakupy","Shopping"),
                new Category(5034, "Kontrola jakości","Quality control"),
                new Category(5035, "Zdrowie / Uroda / Rekreacja","Health / Beauty / Recreation"),
                new Category(5036, "Energetyka","Energetics"),
                new Category(5012, "Inne","Other")
        );
    }

    public List<City> getItJobOffers(ModelMap technology) {

        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<City> cities = initCities();
        List<JustJoinDto> justJoinDtoOffers = getJustJoin();

        cities.forEach(city -> {

                    String selectedCityUTF8 = city.getName().toLowerCase();
                    String selectedCityASCII = removePolishSigns(selectedCityUTF8);
                    WebClient linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
                    WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
                    WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

                    switch(selectedTechnology){
                        case "it category":
                            linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                            noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII);
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
                    city.setNoFluffJobsOffers(getNoFluffJobsOffers(noFluffJobsURL));

                    if(selectedTechnology.equals("it category")){
                        city.setJustJoinOffers((int)justJoinDtoOffers
                                .stream()
                                .filter(filterCity -> {
                                    if(selectedCityUTF8.equals("warszawa")){
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                                        } else if (selectedCityUTF8.equals("kraków")){
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                                        } else {
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                                        }
                                    })
                                .count());
                    } else {
                        city.setJustJoinOffers((int)justJoinDtoOffers
                                .stream()
                                .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                    || filterTechnology.getSkills().stream().allMatch(map -> map.containsValue(selectedTechnology)))
                                .filter(filterCity -> {
                                    if(selectedCityUTF8.equals("warszawa")){
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                                    } else if (selectedCityUTF8.equals("kraków")){
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                                    } else {
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                                    }
                                })
                                .count());
                    }

                    city.setTotalJobOffers(city.getLinkedinOffers() + city.getPracujOffers() + city.getNoFluffJobsOffers() + city.getJustJoinOffers());
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
        List<JustJoinDto> justJoinDtoOffers = getJustJoin();

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();
            WebClient linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityUTF8);
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityUTF8 + ";wp");
            WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

            if(selectedCityUTF8.equals("poland")) {
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw");
                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology);
            }
            if(selectedTechnology.equals("c++")) {
                linkedinURL = WebClient.create("https://pl.linkedin.com/jobs/c++-jobs-polska");
            }

            technology.setLinkedinOffers(getLinkedinOffers(linkedinURL));
            technology.setPracujOffers(getPracujOffers(pracujURL));
            technology.setNoFluffJobsOffers(getNoFluffJobsOffers(noFluffJobsURL));

            if(selectedCityUTF8.equals("poland")){
                technology.setJustJoinOffers((int)justJoinDtoOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().stream().allMatch(map -> map.containsValue(selectedTechnology)))
                        .count());
            } else {
                technology.setJustJoinOffers((int)justJoinDtoOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().stream().allMatch(map -> map.containsValue(selectedTechnology)))
                        .filter(filterCity -> {
                            if(selectedCityUTF8.equals("warszawa")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                            } else if (selectedCityUTF8.equals("kraków")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                            } else {
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                            }
                        })
                        .count());
            }
            technology.setTotalJobOffers(technology.getLinkedinOffers() + technology.getPracujOffers() + technology.getNoFluffJobsOffers() + technology.getJustJoinOffers());
        });

        return technologies;
    }

    public List<CategoryDto> getCategoryStatistics(ModelMap city){

        String selectedCityASCII = removePolishSigns(city.get("city").toString().toLowerCase());
        List<Category> categories = initCategories();

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/" + categoryName + ";cc," + category.getPracujId());
            category.setPracujOffers(getPracujOffers(pracujURL));
        });

        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
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

    private int getNoFluffJobsOffers(WebClient url) {

        NoFluffJobsList postings =  url
                .get()
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(NoFluffJobsList.class)
                .block();

        return Objects.requireNonNull(postings).getPostings().size();
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
