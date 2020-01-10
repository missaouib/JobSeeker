package com.Backend.core.domain;

import com.Backend.core.service.DtoMapper;
import com.Backend.core.service.RequestCreator;
import com.Backend.core.service.UrlBuilder;
import com.Backend.infrastructure.dto.JobsOffersInPolandDto;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ScrapTechnologyInPoland {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersInPolandRepository technologyOffersInPolandRepository;
    private CityRepository cityRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyCityOffersMapper);
    }

    List<JobsOffersInPolandDto> getItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {
        List<TechnologyOffersInPoland> offers = technologyOffersInPolandRepository.findByDateAndTechnology(LocalDate.now(), technologyRepository.findTechnologyByName(technologyName)
                .orElse(null));

        if(technologyName.equals("all technologies")){
            return mapToDto(getAllTechnologies());
        }
        else if (offers.isEmpty()) {
            return mapToDto(scrapItJobsOffersInPoland(technologyName, justJoinItOffers));
        } else {
            return mapToDto(offers);
        }
    }

    private List<JobsOffersInPolandDto> getAllTechnologies(){

        List<Object[]> hibernateObjectList = technologyOffersInPolandRepository.findAllTechnologies(LocalDate.now());
        List<JobsOffersInPolandDto> convertedList = new ArrayList<>();

        for (Object[] line : hibernateObjectList) {
            JobsOffersInPolandDto offer = new JobsOffersInPolandDto();
            offer.setName(line[0].toString());
            offer.setPopulation(Integer.parseInt(line[1].toString()));
            offer.setArea(Double.parseDouble(line[2].toString()));
            offer.setDensity(Integer.parseInt(line[3].toString()));
            offer.setLinkedin(Integer.parseInt(line[4].toString()));
            offer.setIndeed(Integer.parseInt(line[5].toString()));
            offer.setPracuj(Integer.parseInt(line[6].toString()));
            offer.setNoFluffJobs(Integer.parseInt(line[7].toString()));
            offer.setJustJoinIt(Integer.parseInt(line[8].toString()));
            convertedList.add(offer);
        }

        return convertedList;
    }

    private <T> List<JobsOffersInPolandDto> mapToDto(final List<T> offers) {
        return offers.stream()
                .map(offerInPoland -> modelMapper.map(offerInPoland, JobsOffersInPolandDto.class))
                .peek(jobsOffersInPolandDto -> jobsOffersInPolandDto.setTotal(jobsOffersInPolandDto.getLinkedin() + jobsOffersInPolandDto.getIndeed() + jobsOffersInPolandDto.getPracuj() + jobsOffersInPolandDto.getNoFluffJobs() + jobsOffersInPolandDto.getJustJoinIt()))
                .peek(jobsOffersInPolandDto -> jobsOffersInPolandDto.setPer100k(Math.round(jobsOffersInPolandDto.getTotal() * 1.0 / (jobsOffersInPolandDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

    private List<TechnologyOffersInPoland> scrapItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {

        List<City> cities = cityRepository.findAll();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyOffersInPoland> technologyOfferInPoland = new ArrayList<>();

        if (justJoinItOffers.isEmpty()) {
            justJoinItOffers = requestCreator.scrapJustJoinIT();
        }
        List<JustJoinIt> finalJustJoinItOffers = justJoinItOffers;

        cities.forEach(city -> {

            String cityNameUTF8 = city.getName().toLowerCase();
            String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);

            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
            String indeedUrl = UrlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
            String pracujUrl = UrlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
            String noFluffJobsUrl = UrlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);

            TechnologyOffersInPoland offer = new TechnologyOffersInPoland(city, technologyOptional.orElse(null), LocalDate.now());

            try {
                offer.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            offer.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));
            offer.setPracuj(requestCreator.scrapPracujOffers(pracujUrl));
            offer.setNoFluffJobs(requestCreator.scrapNoFluffJobsOffers(noFluffJobsUrl, cityNameUTF8, technologyName));
            offer.setJustJoinIt(requestCreator.extractJustJoinItJson(finalJustJoinItOffers, cityNameUTF8, technologyName));

            technologyOfferInPoland.add(offer);
        });

        return new ArrayList<>(technologyOptional
                .filter(ignoredTechnologyCity -> !technologyOffersInPolandRepository.existsFirstByDateAndTechnology(LocalDate.now(), ignoredTechnologyCity))
                .map(savedTechnologyCity -> technologyOfferInPoland
                        .stream()
                        .map(technologyCity -> technologyOffersInPolandRepository.save(technologyCity))
                        .collect(Collectors.toList()))
                .orElse(technologyOfferInPoland));
    }

}
