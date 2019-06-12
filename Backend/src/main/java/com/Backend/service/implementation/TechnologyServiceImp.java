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
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyOffersRepository = Objects.requireNonNull(technologyOffersRepository);
        this.cityRepository = cityRepository;
    }

    private PropertyMap<TechnologyOffers, TechnologyDto> technologyMapping = new PropertyMap<>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
        }
    };

    public List<TechnologyDto> scrapTechnologyStatistics(ModelMap city) {

        String selectedCityUTF8 = city.get("city").toString().toLowerCase();
        String selectedCityASCII = scrapJobService.removePolishSigns(city.get("city").toString().toLowerCase());
        List<Technology> technologies = technologyRepository.findAll();
        List<JustJoin> justJoinOffers = scrapJobService.getJustJoin();
        List<TechnologyOffers> technologiesOffers = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findCityByName(selectedCityUTF8);

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();
            WebClient linkedinURL = WebClient.create("https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII);
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp");
            WebClient noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology);

            if(selectedCityASCII.equals("poland")) {
                pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedTechnology + ";kw");
                noFluffJobsURL = WebClient.create("https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology);
            }
            if(selectedTechnology.equals("c++")) {
                linkedinURL = WebClient.create("https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII);
            }

            TechnologyOffers technologyOffers = new TechnologyOffers(technology, cityOptional.orElse(null), LocalDate.now());

            technologyOffers.setLinkedin(scrapJobService.getLinkedinOffers(linkedinURL));
            technologyOffers.setPracuj(scrapJobService.getPracujOffers(pracujURL));
            technologyOffers.setNoFluffJobs(scrapJobService.getNoFluffJobsOffers(noFluffJobsURL));

            if(selectedCityASCII.equals("poland")){
                technologyOffers.setJustJoin((int) justJoinOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
                        .count());
            } else {
                technologyOffers.setJustJoin((int) justJoinOffers
                        .stream()
                        .filter(filterTechnology -> filterTechnology.getTitle().toLowerCase().contains(selectedTechnology)
                                || filterTechnology.getSkills().get(0).get("name").toLowerCase().contains(selectedTechnology))
                        .filter(filterCity -> {
                            if(selectedCityASCII.equals("warszawa")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("warsaw"));
                            } else if (selectedCityASCII.equals("kraków")){
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII) || filterCity.getCity().toLowerCase().contains("cracow"));
                            } else {
                                return (filterCity.getCity().toLowerCase().contains(selectedCityUTF8) || filterCity.getCity().toLowerCase().contains(selectedCityASCII));
                            }
                        })
                        .count());
            }
            technologiesOffers.add(technologyOffers);
        });

        return cityOptional
                .map(ignored -> technologiesOffers.stream().map(technology -> modelMapper.map(technologyOffersRepository.save(technology), TechnologyDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> technologiesOffers.stream().map(technology -> modelMapper.map(technology, TechnologyDto.class)).collect(Collectors.toList()));
    }
}
