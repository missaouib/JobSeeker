package com.Backend.service.implementation;

import com.Backend.entity.Category;
import com.Backend.entity.City;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.domain.*;
import com.Backend.dto.CategoryDto;
import com.Backend.entity.offers.CategoryOffers;
import com.Backend.repository.CategoryRepository;
import com.Backend.repository.CityRepository;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.CategoryOffersRepository;
import com.Backend.repository.offers.CityOffersRepository;
import com.Backend.repository.offers.CountryOffersRepository;
import com.Backend.repository.offers.TechnologyOffersRepository;
import com.Backend.service.ScrapJobService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScrapJobServiceImp implements ScrapJobService {

    private ModelMapper modelMapper;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private TechnologyRepository technologyRepository;
    private CityOffersRepository cityOffersRepository;
    private CountryOffersRepository countryOffersRepository;
    private TechnologyOffersRepository technologyOffersRepository;

    ScrapJobServiceImp(ModelMapper modelMapper, CityRepository cityRepository, CountryRepository countryRepository, TechnologyRepository technologyRepository,
                       CityOffersRepository cityOffersRepository, CountryOffersRepository countryOffersRepository, TechnologyOffersRepository technologyOffersRepository){
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.cityOffersRepository = Objects.requireNonNull(cityOffersRepository);
        this.countryOffersRepository = Objects.requireNonNull(countryOffersRepository);
        this.technologyOffersRepository = Objects.requireNonNull(technologyOffersRepository);
    }

    public List<City> getItJobOffersInPoland(ModelMap technology) {

        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<City> cities = cityRepository.findAll();
        List<JustJoin> justJoinOffers = getJustJoin();

//        cities.forEach(city -> {
//
//                    String selectedCityUTF8 = city.getName().toLowerCase();
//                    String selectedCityASCII = removePolishSigns(selectedCityUTF8);
//                    WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
//                    WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
//                    WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);
//
//                    switch(selectedTechnology){
//                        case "it category":
//                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
//                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
//                            noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII);
//                            break;
//                        case "c++":
//                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
//                            break;
//                        case "c#":
//                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCityASCII);
//                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/c%23;kw/" + selectedCityASCII + ";wp");
//                            break;
//                    }
//
//                    city.setLinkedinOffers(getLinkedinOffers(linkedinURL));
//                    city.setPracujOffers(getPracujOffers(pracujURL));
//                    city.setNoFluffJobsOffers(getNoFluffJobsOffers(noFluffJobsURL));
//
//                    if(selectedTechnology.equals("it category")){
//                        city.setJustJoinOffers((int) justJoinOffers
//                                .stream()
//                                .filter(filterCity -> {
//                                    if(selectedCityUTF8.equals("warszawa")){
//                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
//                                        } else if (selectedCityUTF8.equals("kraków")){
//                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
//                                        } else {
//                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
//                                        }
//                                    })
//                                .count());
//                    } else {
//                        city.setJustJoinOffers((int) justJoinOffers
//                                .stream()
//                                .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
//                                    || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
//                                .filter(filterCity -> {
//                                    if(selectedCityUTF8.equals("warszawa")){
//                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
//                                    } else if (selectedCityUTF8.equals("kraków")){
//                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
//                                    } else {
//                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
//                                    }
//                                })
//                                .count());
//                    }
//
//                    city.setTotalJobOffers(city.getLinkedinOffers() + city.getPracujOffers() + city.getNoFluffJobsOffers() + city.getJustJoinOffers());
//                    city.setJobOfferPer100kCitizens((double) Math.round(((city.getPracujOffers() + city.getLinkedinOffers() + city.getJustJoinOffers() + city.getNoFluffJobsOffers()) / 4.0 * 1.0 / (city.getPopulation() * 1.0 / 100000)) * 100) / 100);
//                    city.setDensity((int) Math.round(city.getPopulation() / city.getArea()));
//                }
//        );

        return cities;
    }

    @Override
    public List<Country> getItJobOffersInWorld(ModelMap technology) {

        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<Country> countries = countryRepository.findAll();

//        countries.forEach(country -> {
//
//            String selectedCountry = country.getName().toLowerCase();
//
//            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCountry);
//
//            switch(selectedTechnology){
//                case "it category":
//                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCountry + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
//                    break;
//                case "c++":
//                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCountry);
//                    break;
//                case "c#":
//                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCountry);
//                    break;
//            }
//
//            country.setLinkedinOffers(getLinkedinOffers(linkedinURL));
//            country.setJobOfferPer100kCitizens((double) Math.round(country.getLinkedinOffers() * 1.0 / (country.getPopulation() * 1.0 / 100000) * 100) / 100);
//            country.setDensity((double)Math.round(country.getPopulation() / country.getArea() * 100) / 100);
//        });

        return countries;
    }

    @Override
    public List<Technology> getTechnologyStatistics(ModelMap city) {

        String selectedCityUTF8 = city.get("city").toString().toLowerCase();
        String selectedCityASCII = removePolishSigns(city.get("city").toString().toLowerCase());
        List<Technology> technologies = technologyRepository.findAll();
        List<JustJoin> justJoinOffers = getJustJoin();

//        technologies.forEach(technology -> {
//
//            String selectedTechnology = technology.getName().toLowerCase();
//            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
//            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
//            WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);
//
//            if(selectedCityASCII.equals("poland")) {
//                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw");
//                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology);
//            }
//            if(selectedTechnology.equals("c++")) {
//                linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
//            }
//
//            technology.setLinkedinOffers(getLinkedinOffers(linkedinURL));
//            technology.setPracujOffers(getPracujOffers(pracujURL));
//            technology.setNoFluffJobsOffers(getNoFluffJobsOffers(noFluffJobsURL));
//
//            if(selectedCityASCII.equals("poland")){
//                technology.setJustJoinOffers((int) justJoinOffers
//                        .stream()
//                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
//                                || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
//                        .count());
//            } else {
//                technology.setJustJoinOffers((int) justJoinOffers
//                        .stream()
//                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
//                                || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
//                        .filter(filterCity -> {
//                            if(selectedCityASCII.equals("warszawa")){
//                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
//                            } else if (selectedCityASCII.equals("kraków")){
//                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
//                            } else {
//                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
//                            }
//                        })
//                        .count());
//            }
//            technology.setTotalJobOffers(technology.getLinkedinOffers() + technology.getPracujOffers() + technology.getNoFluffJobsOffers() + technology.getJustJoinOffers());
//        });

        return technologies;
    }

    public int getLinkedinOffers(WebClient url) {

        Mono<ClientResponse> result = url.get()
                .exchange();

        String resultString = result.flatMap(res -> res.bodyToMono(String.class)).block();

        // Can be changed in future by site owner
        String htmlFirstTag = "Past Month <span class=\"filter-list__label-count\">(";
        String htmlLastTag = ")</span></label></li><li class=\"filter-list__list-item filter-button-dropdown__list-item\"><input type=\"radio\" name=\"f_TP\" value=\"\" id=\"TIME_POSTED-3\" checked>";

        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstTag) && resultString.contains(htmlLastTag)) {
            jobAmount = Integer.valueOf(resultString.substring(resultString.indexOf(htmlFirstTag) + htmlFirstTag.length(), resultString.indexOf(htmlLastTag)).replaceAll(",", ""));
        }

        return jobAmount;
    }

    public int getPracujOffers(WebClient url) {

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

    public int getNoFluffJobsOffers(WebClient url) {

        NoFluffJobsList postings =  url
                .get()
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
