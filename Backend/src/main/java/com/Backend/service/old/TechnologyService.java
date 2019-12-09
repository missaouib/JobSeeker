package com.Backend.service.old;

import com.Backend.domain.JustJoinIT;
import com.Backend.dto.TechnologyDto;
import com.Backend.entity.City;
import com.Backend.entity.Country;
import com.Backend.entity.Technology;
import com.Backend.entity.offers.CountryOffers;
import com.Backend.entity.offers.TechnologyOffers;
import com.Backend.repository.CityRepository;
import com.Backend.repository.CountryRepository;
import com.Backend.repository.TechnologyRepository;
import com.Backend.repository.offers.TechnologyOffersRepository;
import com.Backend.service.RequestService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnologyService {

    private ModelMapper modelMapper;
    private RequestService requestService;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersRepository technologyOffersRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public TechnologyService(ModelMapper modelMapper, RequestService requestService, TechnologyRepository technologyRepository,
                             TechnologyOffersRepository technologyOffersRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(technologyMapping);
        this.modelMapper.addConverter(totalConverter);
        this.requestService = Objects.requireNonNull(requestService);
        this.technologyRepository = Objects.requireNonNull(technologyRepository);
        this.technologyOffersRepository = Objects.requireNonNull(technologyOffersRepository);
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
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

    public List<TechnologyDto> getTechnologyStatistics(String city) {

        List<TechnologyOffers> list = technologyOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(city).orElse(null));

        if(list.isEmpty()){
            return scrapTechnologyStatistics(city);
        } else {
            return list.stream().map(technology -> modelMapper.map(technology, TechnologyDto.class)).collect(Collectors.toList());
        }
    }

    public List<CountryOffers> scrapTechnologyStatisticsForCountries(String country) {
        String selectedCountryUTF8 = country.toLowerCase();
        List<Technology> technologies = technologyRepository.findAll();
        List<CountryOffers> countriesOffers = new ArrayList<>();
        Optional<Country> countryOptional = countryRepository.findCountryByName(selectedCountryUTF8);

        technologies.forEach(technology -> {
            String selectedTechnology = technology.getName().toLowerCase();

            String linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCountryUTF8;
            String IndeedDynamicURL = "https://" + countryOptional.get().getCode() + ".indeed.com/" + selectedTechnology + "-jobs";

            switch(countryOptional.get().getCode()) {
                case "us":
                    IndeedDynamicURL = "https://indeed.com/" + selectedTechnology + "-jobs";
                    break;
                case "my":
                    IndeedDynamicURL = "https://indeed.com.my/" + selectedTechnology + "-jobs";
            }

            CountryOffers countryOffers = new CountryOffers(countryOptional.orElse(null), technology, LocalDate.now());

            try {
                countryOffers.setIndeed(requestService.scrapIndeedOffers(IndeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }
            countriesOffers.add(countryOffers);
        });

        return countriesOffers;
    }

    //SELECT * FROM CityOffers WHERE technology = this.technology

    public List<TechnologyDto> scrapTechnologyStatistics(String city) {
        String selectedCityUTF8 = city.toLowerCase();
        String selectedCityASCII = requestService.removePolishSigns(selectedCityUTF8);
        List<Technology> technologies = technologyRepository.findAll();
        List<JustJoinIT> justJoinITOffers = requestService.scrapJustJoin();
        List<TechnologyOffers> technologiesOffers = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findCityByName(selectedCityUTF8);

        //REMOVE THIS LOL
//        if(selectedCityASCII.equals("all cities")){
//            selectedCityASCII = selectedCityASCII.replaceAll("all cities", "poland");
//        }

        technologies.forEach(technology -> {

            String selectedTechnology = technology.getName().toLowerCase();

            String linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=" + selectedTechnology + "&location=" + selectedCityASCII;
            String indeedDynamicURL = "https://pl.indeed.com/Praca-" + selectedTechnology + "-w-" + selectedCityASCII;
            String pracujDynamicURL = "https://www.pracuj.pl/praca/" + selectedTechnology + ";kw/" + selectedCityASCII + ";wp";
            String noFluffJobsDynamicURL = "https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII + "+" + selectedTechnology;

            switch(selectedTechnology){
                case "all jobs":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=&location=" + selectedCityASCII;
                    pracujDynamicURL = "https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp";
                    noFluffJobsDynamicURL = "https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII;
                    if(selectedCityASCII.equals("poland")) {
                        noFluffJobsDynamicURL = "https://nofluffjobs.com/api/search/posting";
                    }
                    break;
                case "all it jobs":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/search?location=" + selectedCityASCII + "&pageNum=0&position=1&f_TP=1%2C2%2C3%2C4&f_I=96";
                    pracujDynamicURL = "https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/it%20-%20rozw%c3%b3j%20oprogramowania;cc,5016";
                    noFluffJobsDynamicURL = "https://nofluffjobs.com/api/search/posting?criteria=city=" + selectedCityASCII;
                    if(selectedCityASCII.equals("poland")) {
                        noFluffJobsDynamicURL = "https://nofluffjobs.com/api/search/posting";
                    }
                    break;
                case "c++":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII;
                    break;
                case "c#":
                    linkedinDynamicURL = "https://www.linkedin.com/jobs/search?keywords=C%23&location=" + selectedCityASCII;
                    pracujDynamicURL = "https://www.pracuj.pl/praca/c%23;kw/" + selectedCityASCII + ";wp";
                    break;
            }

            if(selectedCityASCII.equals("poland") && !selectedTechnology.equals("all it jobs") && !selectedTechnology.equals("all jobs")) {
                pracujDynamicURL = "https://www.pracuj.pl/praca/" + selectedTechnology + ";kw";
                noFluffJobsDynamicURL = "https://nofluffjobs.com/api/search/posting?criteria=" + selectedTechnology;
                linkedinDynamicURL = "https://www.linkedin.com/jobs/" + selectedTechnology + "-jobs-poland";
            }
            if(selectedCityASCII.equals("poland") && selectedTechnology.equals("c++")){
                linkedinDynamicURL = "https://www.linkedin.com/jobs/c++-jobs-poland";
            }
            else if(selectedTechnology.equals("c++")) {
                linkedinDynamicURL = "https://www.linkedin.com/jobs/c++-jobs-" + selectedCityASCII;
            }

            TechnologyOffers technologyOffers = new TechnologyOffers(technology, cityOptional.orElse(null), LocalDate.now());

            technologyOffers.setLinkedin(requestService.scrapLinkedinOffers(linkedinDynamicURL));
            try {
                technologyOffers.setIndeed(requestService.scrapIndeedOffers(indeedDynamicURL));
            } catch (IOException e) {
                e.printStackTrace();
            }
            technologyOffers.setPracuj(requestService.scrapPracujOffers(pracujDynamicURL));
            technologyOffers.setNoFluffJobs(requestService.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));

            if(selectedCityASCII.equals("poland")){
                technologyOffers.setJustJoinIT((int) justJoinITOffers
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
                technologyOffers.setJustJoinIT((int) justJoinITOffers
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
                .filter(ignoredCity -> !technologyOffersRepository.existsFirstByDateAndCity(LocalDate.now(), ignoredCity))
                .map(ignoredCity -> technologiesOffers.stream().map(category -> modelMapper.map(technologyOffersRepository.save(category), TechnologyDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> technologiesOffers.stream().map(category -> modelMapper.map(category, TechnologyDto.class)).collect(Collectors.toList()));
    }

}
