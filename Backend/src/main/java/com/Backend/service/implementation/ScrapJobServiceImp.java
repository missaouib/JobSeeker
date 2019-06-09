package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.domain.NoFluffJobsList;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.CountryOffersRepository;
import com.Backend.repository.offers.TechnologyOffersRepository;
import com.Backend.service.ScrapJobService;
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
public class ScrapJobServiceImp implements ScrapJobService {

    private CountryRepository countryRepository;
    private TechnologyRepository technologyRepository;
    private CountryOffersRepository countryOffersRepository;
    private TechnologyOffersRepository technologyOffersRepository;

    ScrapJobServiceImp(CountryRepository countryRepository, TechnologyRepository technologyRepository,
                       CountryOffersRepository countryOffersRepository, TechnologyOffersRepository technologyOffersRepository){
        this.countryRepository = Objects.requireNonNull(countryRepository);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.countryOffersRepository = Objects.requireNonNull(countryOffersRepository);
        this.technologyOffersRepository = Objects.requireNonNull(technologyOffersRepository);
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
