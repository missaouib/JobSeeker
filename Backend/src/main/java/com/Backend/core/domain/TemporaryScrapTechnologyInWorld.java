package com.Backend.core.domain;

import com.Backend.core.service.DtoMapper;
import com.Backend.core.service.RequestCreator;
import com.Backend.core.service.UrlBuilder;
import com.Backend.infrastructure.dto.TechnologyStatisticsInWorldDto;
import com.Backend.infrastructure.entity.Country;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInWorld;
import com.Backend.infrastructure.repository.CountryRepository;
import com.Backend.infrastructure.repository.TechnologyOffersInWorldRepository;
import com.Backend.infrastructure.repository.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TemporaryScrapTechnologyInWorld {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private TechnologyRepository technologyRepository;
    private TechnologyOffersInWorldRepository technologyOffersInWorldRepository;
    private CountryRepository countryRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.technologyStatisticsInWorldMapper);
    }

    public List<TechnologyStatisticsInWorldDto> loadTechnologyStatisticsInWorld(String countryName) {
        List<TechnologyOffersInWorld> offers = technologyOffersInWorldRepository.findByDateAndCountry(LocalDate.now(), countryRepository.findCountryByName(countryName)
                .orElse(null));

        if(countryName.equals("all countries")) {
            return mapToDto(getAllTechnologies());
        }
        else if (offers.size() < 42) {
            return mapToDto(scrapTechnologyStatisticsInWorld(countryName));
        } else {
            return mapToDto(offers);
        }
    }

    private List<TechnologyStatisticsInWorldDto> getAllTechnologies(){

        List<Object[]> hibernateObjectList = technologyOffersInWorldRepository.findAllTechnologiesInTechnologyStatsInWorld(LocalDate.now());
        List<TechnologyStatisticsInWorldDto> convertedList = new ArrayList<>();

        for (Object[] line : hibernateObjectList) {
            TechnologyStatisticsInWorldDto offer = new TechnologyStatisticsInWorldDto();
            offer.setName(line[0].toString());
            offer.setType(line[1].toString());
            offer.setLinkedin(Integer.parseInt(line[2].toString()));
            offer.setIndeed(Integer.parseInt(line[3].toString()));
            convertedList.add(offer);
        }

        return convertedList;
    }

    private <T> List<TechnologyStatisticsInWorldDto> mapToDto(final List<T> offers) {
        return offers.stream()
                .map(technologyStatisticsInWorld -> modelMapper.map(technologyStatisticsInWorld, TechnologyStatisticsInWorldDto.class))
                .peek(jobsOffersInWorldDto -> jobsOffersInWorldDto.setTotal(jobsOffersInWorldDto.getLinkedin() + jobsOffersInWorldDto.getIndeed()))
                .collect(Collectors.toList());
    }

    public List<TechnologyOffersInWorld> scrapTechnologyStatisticsInWorld(String countryName) {

        String countryNameUTF8 = countryName.toLowerCase();
        List<TechnologyOffersInWorld> countriesOffers = new ArrayList<>();
        List<Technology> technologies = technologyRepository.findAll();
        Country country = countryRepository.findCountryByName(countryNameUTF8).orElse(null);

        technologies.forEach(technology -> {

            String technologyName = technology.getName().toLowerCase();
            String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);

            TechnologyOffersInWorld technologyCountryOffers = new TechnologyOffersInWorld(country, technology, LocalDate.now());

            if (country != null) {
                String indeedUrl = UrlBuilder.indeedBuildUrlForCountry(technologyName, country.getCode());

                try {

                    technologyCountryOffers.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            technologyCountryOffers.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));

            countriesOffers.add(technologyCountryOffers);
        });

        return countriesOffers;
    }

}
