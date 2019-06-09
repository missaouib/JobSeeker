package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.dto.CityDto;
import com.Backend.entity.City;
import com.Backend.entity.offers.CityOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.offers.CityOffersRepository;
import com.Backend.service.CityService;
import com.Backend.service.ScrapJobService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CityServiceImp implements CityService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;
    private CityRepository cityRepository;
    private CityOffersRepository cityOffersRepository;

    public CityServiceImp(ModelMapper modelMapper, ScrapJobService scrapJobService, CityRepository cityRepository, CityOffersRepository cityOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(cityMapping);
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.cityOffersRepository = Objects.requireNonNull(cityOffersRepository);
    }

    private PropertyMap<CityOffers, CityDto> cityMapping = new PropertyMap<>() {
        protected void configure() {
            map().setName(source.getCity().getName());
            map().setPopulation(source.getCity().getPopulation());
            map().setArea(source.getCity().getArea());
            map().setDensity(source.getCity().getDensity());
        }
    };

    public List<CityDto> scrapItJobOffersInPoland(ModelMap technology) {
        String selectedTechnology = technology.get("technology").toString().toLowerCase();
        List<City> cities = cityRepository.findAll();
        List<JustJoin> justJoinOffers = scrapJobService.getJustJoin();
        List<CityOffers> citiesOffers = new ArrayList<>();

        cities.forEach(city -> {

                    String selectedCityUTF8 = city.getName().toLowerCase();
                    String selectedCityASCII = scrapJobService.removePolishSigns(selectedCityUTF8);
                    WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
                    WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
                    WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

                    switch(selectedTechnology){
                        case "it category":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                            noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII);
                            break;
                        case "c++":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
                            break;
                        case "c#":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCityASCII);
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/c%23;kw/" + selectedCityASCII + ";wp");
                            break;
                    }

                    CityOffers cityOffer = new CityOffers(city, LocalDate.now());
                    cityOffer.setLinkedin(scrapJobService.getLinkedinOffers(linkedinURL));
                    cityOffer.setPracuj(scrapJobService.getPracujOffers(pracujURL));
                    cityOffer.setNoFluffJobs(scrapJobService.getNoFluffJobsOffers(noFluffJobsURL));

                    if(selectedTechnology.equals("it category")){
                        cityOffer.setJustJoin((int) justJoinOffers
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
                        cityOffer.setJustJoin((int) justJoinOffers
                                .stream()
                                .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                    || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
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

                    cityOffer.setTotal(cityOffer.getLinkedin() + cityOffer.getPracuj() + cityOffer.getNoFluffJobs() + cityOffer.getJustJoin());
                    cityOffer.setPer100k((double) Math.round((cityOffer.getTotal() / 4.0 * 1.0 / (city.getPopulation() * 1.0 / 100000)) * 100) / 100);
                    citiesOffers.add(cityOffer);
                }
        );

        return citiesOffers.stream().map(city -> modelMapper.map(cityOffersRepository.save(city), CityDto.class)).collect(Collectors.toList());
    }

    public List<CityDto> getLastDataFromDB() {
        return null;
    }
}
