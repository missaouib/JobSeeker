package com.Backend.service.implementation;

import com.Backend.domain.JustJoin;
import com.Backend.dto.TechnologyDto;
import com.Backend.entity.City;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyOffersRepository;
import com.Backend.service.ScrapJobService;
import com.Backend.service.TechnologyService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnologyServiceImp implements TechnologyService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersRepository technologyOffersRepository;
    private CityRepository cityRepository;

    public TechnologyServiceImp(ModelMapper modelMapper, ScrapJobService scrapJobService, TechnologyRepository technologyRepository,
                                TechnologyOffersRepository technologyOffersRepository, CityRepository cityRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(technologyMapping);
        this.modelMapper.addConverter(totalConverter);
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyOffersRepository = Objects.requireNonNull(technologyOffersRepository);
        this.cityRepository = cityRepository;
    }

    private PropertyMap<TechnologyOffers, TechnologyDto> technologyMapping = new PropertyMap<TechnologyOffers, TechnologyDto>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
            map().setId(source.getTechnology().getId());
            using(totalConverter).map(map().getTotal());
        }
    };

    private Converter<Integer, Integer> totalConverter = context -> {
        TechnologyOffers technology = (TechnologyOffers) context.getParent().getSource();
        return technology.getLinkedin() + technology.getPracuj() + technology.getNoFluffJobs() + technology.getJustJoin();
    };

    @Override
    public List<TechnologyDto> getTechnologyStatistics(String city) {

        List<TechnologyOffers> list = technologyOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(city).orElse(null));

        if(list.isEmpty()){
            return scrapTechnologyStatistics(city);
        } else {
            return list.stream().map(technology -> modelMapper.map(technology, TechnologyDto.class)).collect(Collectors.toList());
        }
    }

    @Override
    public List<TechnologyDto> scrapTechnologyStatistics(String city) {
        String selectedCityUTF8 = city.toLowerCase();
        String selectedCityASCII = scrapJobService.removePolishSigns(selectedCityUTF8);
        List<Technology> technologies = technologyRepository.findAll();
        List<JustJoin> justJoinOffers = scrapJobService.getJustJoin();
        List<TechnologyOffers> technologiesOffers = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findCityByName(selectedCityUTF8);

        if(selectedCityASCII.equals("all cities")){
            selectedCityASCII = selectedCityASCII.replaceAll("all cities", "poland");
        }
        String finalSelectedCityASCII = selectedCityASCII;

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();
            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + finalSelectedCityASCII);
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + finalSelectedCityASCII + ";wp");
            WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + finalSelectedCityASCII + "+" + selectedTechnology);

            switch(selectedTechnology){
                case "all jobs":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=&location=" + finalSelectedCityASCII);
                    pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + finalSelectedCityASCII + ";wp");
                    noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + finalSelectedCityASCII);
                    if(finalSelectedCityASCII.equals("poland")) {
                        noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting");
                    }
                    break;
                case "all it jobs":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?location=" + finalSelectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96");
                    pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + finalSelectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016");
                    noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + finalSelectedCityASCII);
                    if(finalSelectedCityASCII.equals("poland")) {
                        noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting");
                    }
                    break;
                case "c++":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + finalSelectedCityASCII);
                    break;
                case "c#":
                    linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=C%23&location=" + finalSelectedCityASCII);
                    pracujURL = WebClient.create("https://www.pracuj.pl/praca/c%23;kw/" + finalSelectedCityASCII + ";wp");
                    break;
            }

            if(finalSelectedCityASCII.equals("poland") && !selectedTechnology.equals("all it jobs") && !selectedTechnology.equals("all jobs")) {
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw");
                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology);
                linkedinURL = WebClient.create("https://www.linkedin.com/jobs/" + selectedTechnology + "-jobs-poland");
            }
            if(finalSelectedCityASCII.equals("poland") && selectedTechnology.equals("c++")){
                linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-poland");
            }
            else if(selectedTechnology.equals("c++")) {
                linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + finalSelectedCityASCII);
            }

            TechnologyOffers technologyOffers = new TechnologyOffers(technology, cityOptional.orElse(null), LocalDate.now());

            technologyOffers.setLinkedin(scrapJobService.getLinkedinOffers(linkedinURL));
            technologyOffers.setPracuj(scrapJobService.getPracujOffers(pracujURL));
            technologyOffers.setNoFluffJobs(scrapJobService.getNoFluffJobsOffers(noFluffJobsURL));

            if(finalSelectedCityASCII.equals("poland")){
                technologyOffers.setJustJoin((int) justJoinOffers
                        .stream()
                        .filter(filterTechnology -> {
                            if(selectedTechnology.equals("all jobs") || selectedTechnology.equals("all it jobs")){
                                return true;
                            } else {
                                return filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                        || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology);
                            }
                        })
                        .count());
            } else {
                technologyOffers.setJustJoin((int) justJoinOffers
                        .stream()
                        .filter(filterTechnology -> {
                            if(selectedTechnology.equals("all jobs") || selectedTechnology.equals("all it jobs")) {
                                return true;
                            } else {
                                return filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                        || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology);
                            }
                        })
                        .filter(filterCity -> {
                            if(finalSelectedCityASCII.equals("warszawa")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                            } else if (finalSelectedCityASCII.equals("kraków")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                            } else {
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(finalSelectedCityASCII));
                            }
                        })
                        .count());
            }
            technologiesOffers.add(technologyOffers);
        });

        return cityOptional
                .filter(ignoredCity -> !technologyOffersRepository.existsFirstByDateAndCity(LocalDate.now(), ignoredCity))
                .map(ignoredCity -> technologiesOffers.stream().map(category -> modelMapper.map(technologyOffersRepository.save(category), TechnologyDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> technologiesOffers.stream().map(category -> modelMapper.map(category, TechnologyDto.class)).collect(Collectors.toList()));
    }

}
