package com.Backend.domain;

import com.Backend.infrastructure.dto.JobsOffersInPolandDto;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import com.Backend.infrastructure.model.JustJoinIt;
import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInPolandRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
import com.Backend.service.DtoMapper;
import com.Backend.service.RequestCreator;
import com.Backend.service.UrlBuilder;
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
                .orElseThrow(IllegalStateException::new));

        if (offers.isEmpty()) {
            return mapToDto(scrapItJobsOffersInPoland(technologyName, justJoinItOffers));
        } else {
            return mapToDto(offers);
        }
    }

    private <T> List<JobsOffersInPolandDto> mapToDto(final List<T> offers) {
        return offers.stream()
                .map(offerInPoland -> modelMapper.map(offerInPoland, JobsOffersInPolandDto.class))
                .peek(jobsOffersInPolandDto -> jobsOffersInPolandDto.setTotal(jobsOffersInPolandDto.getLinkedin() + jobsOffersInPolandDto.getPracuj() + jobsOffersInPolandDto.getNoFluffJobs() + jobsOffersInPolandDto.getJustJoinIT()))
                .peek(jobsOffersInPolandDto -> jobsOffersInPolandDto.setPer100k(Math.round(jobsOffersInPolandDto.getTotal() * 1.0 / (jobsOffersInPolandDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
                .collect(Collectors.toList());
    }

    private List<TechnologyOffersInPoland> scrapItJobsOffersInPoland(String technologyName, List<JustJoinIt> justJoinItOffers) {

        List<City> cities = cityRepository.findAll();
        Optional<Technology> technologyOptional = technologyRepository.findTechnologyByName(technologyName);
        List<TechnologyOffersInPoland> technologyOfferInPoland = new ArrayList<>();

        if(justJoinItOffers.isEmpty()){
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
            offer.setNoFluffJobs(requestCreator.scrapNoFluffJobsOffers(noFluffJobsUrl));
            offer.setJustJoinIt(requestCreator.extractJustJoinItJson(finalJustJoinItOffers, city.getName(), technologyName));

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
