package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.dto.CityDto;
import com.Backend.dto.TechnologyDto;
import com.Backend.entity.City;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.CityOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.CityOffersRepository;
import com.Backend.service.CityService;
import com.Backend.service.ScrapJobService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImp implements CityService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;
    private CityRepository cityRepository;
    private CityOffersRepository cityOffersRepository;
    private TechnologyRepository technologyRepository;

    public CityServiceImp(ModelMapper modelMapper, ScrapJobService scrapJobService, CityRepository cityRepository,
                          CityOffersRepository cityOffersRepository, TechnologyRepository technologyRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(cityMapping);
        this.modelMapper.addConverter(totalConverter);
        //this.modelMapper.addConverter(per100kConverter);
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
        this.cityRepository = Objects.requireNonNull(cityRepository);
        this.cityOffersRepository = Objects.requireNonNull(cityOffersRepository);
        this.technologyRepository = technologyRepository;
    }

    private PropertyMap<CityOffers, CityDto> cityMapping = new PropertyMap<CityOffers, CityDto>() {
        protected void configure() {
            map().setName(source.getCity().getName());
            map().setPopulation(source.getCity().getPopulation());
            map().setArea(source.getCity().getArea());
            map().setDensity(source.getCity().getDensity());
            map().setId(source.getCity().getId());
            using(totalConverter).map(map().getTotal());
            //using(per100kConverter).map(map().getPer100k());
        }
    };

    private Converter<Integer, Integer> totalConverter = context -> {
        CityOffers city = (CityOffers) context.getParent().getSource();
        return city.getLinkedin() + city.getPracuj() + city.getNoFluffJobs() + city.getJustJoin();
    };

//    private Converter<Double, Double> per100kConverter = context -> {
//        City city = (City) context.getParent().getSource();
//        CityOffers cityOffers = (CityOffers) context.getParent().getSource();
//        return Math.round(cityOffers.getJustJoin() * 1.0 / (city.getPopulation() * 1.0 / 100000) * 100.0) / 100.0;
//    };

    @Override
    public List<CityDto> getItJobOffersInPoland(String technology) {

        List<CityOffers> list = cityOffersRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technology).orElse(null));

//        return Optional.of(cityOffersRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technology).orElse(null)))
//                //.stream()
//                .map(city -> {
//                    CityDto cityDto = modelMapper.map(city, CityDto.class);
//                    cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0);
//                    return cityDto;
//                })
//                .orElseGet(() -> scrapItJobOffersInPoland(technology).stream().peek(
//                    cityDto -> cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0)
//                ).collect(Collectors.toList()));

        if(list.isEmpty()){
            return scrapItJobOffersInPoland(technology).stream().peek(
                    cityDto -> cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0)
            ).collect(Collectors.toList());
        } else {
            return list.stream().map(city -> {
                CityDto cityDto = modelMapper.map(city, CityDto.class);
                cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0);
                return cityDto;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<CityDto> scrapItJobOffersInPoland(String technology) {
        String selectedTechnology = technology.toLowerCase();
        List<City> cities = cityRepository.findAll();
        List<JustJoin> justJoinOffers = scrapJobService.getJustJoin();
        List<CityOffers> citiesOffers = new ArrayList<>();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(selectedTechnology);

        cities.forEach(city -> {

                    String selectedCityUTF8 = city.getName().toLowerCase();
                    String selectedCityASCII = scrapJobService.removePolishSigns(selectedCityUTF8);

                    if(selectedCityASCII.equals("all cities")){
                        selectedCityASCII = selectedCityASCII.replaceAll("all cities", "poland");
                    }

                    WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
                    WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
                    WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

                    switch(selectedTechnology){
                        case "all jobs":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=&location=" + selectedCityASCII);
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp");
                            noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII);
                            if(selectedCityASCII.equals("poland")) {
                                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting");
                            }
                            break;
                        case "all it jobs":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                            noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII);
                            if(selectedCityASCII.equals("poland")) {
                                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting");
                            }
                            break;
                        case "c++":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
                            break;
                        case "c#":
                            linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCityASCII);
                            pracujURL = WebClient.create("https://www.pracuj.pl/praca/c%23;kw/" + selectedCityASCII + ";wp");
                            break;
                    }

                    if(selectedCityASCII.equals("poland") && !selectedTechnology.equals("all it jobs") && !selectedTechnology.equals("all jobs")) {
                        noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology);
                    }

                    CityOffers cityOffer = new CityOffers(city, technologyOptional.orElse(null), LocalDate.now());

                    cityOffer.setLinkedin(scrapJobService.getLinkedinOffers(linkedinURL));
                    cityOffer.setPracuj(scrapJobService.getPracujOffers(pracujURL));
                    cityOffer.setNoFluffJobs(scrapJobService.getNoFluffJobsOffers(noFluffJobsURL));

                    String finalSelectedCityASCII = selectedCityASCII;
                    if(selectedTechnology.equals("all it jobs") || selectedTechnology.equals("all jobs") ){
                        cityOffer.setJustJoin((int) justJoinOffers
                                .stream()
                                .filter(filterCity -> {
                                    if(selectedCityUTF8.equals("warszawa")){
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                                        } else if (selectedCityUTF8.equals("kraków")) {
                                              return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                                        } else if(finalSelectedCityASCII.equals("poland")) {
                                            return true;
                                        } else {
                                            return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII));
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
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                                    } else if (selectedCityUTF8.equals("kraków")){
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                                    } else if(finalSelectedCityASCII.equals("poland")) {
                                        return true;
                                    } else {
                                        return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII));
                                    }
                                })
                                .count());
                    }
                    citiesOffers.add(cityOffer);
                }
        );

        return technologyOptional
                .filter(ignoredTechnology -> !cityOffersRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnology))
                .map(ignoredCity -> citiesOffers.stream()
                        .map(city -> modelMapper.map(cityOffersRepository.save(city), CityDto.class))
                        .collect(Collectors.toList()))
                .orElseGet(() -> citiesOffers.stream()
                        .map(city -> modelMapper.map(city, CityDto.class))
                        .collect(Collectors.toList()));
    }

}
