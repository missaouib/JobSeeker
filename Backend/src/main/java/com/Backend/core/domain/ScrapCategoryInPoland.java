package com.Backend.core.domain;

import com.Backend.infrastructure.dto.CategoryStatisticsInPolandDto;
import com.Backend.infrastructure.entity.Category;
import com.Backend.infrastructure.entity.CategoryOffersInPoland;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.repository.CategoryOffersInPolandRepository;
import com.Backend.infrastructure.repository.CategoryRepository;
import com.Backend.infrastructure.repository.CityRepository;
import com.Backend.core.service.DtoMapper;
import com.Backend.core.service.RequestCreator;
import com.Backend.core.service.UrlBuilder;
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
class ScrapCategoryInPoland {

    private static final int PLACEHOLDER_OFFER = 0;
    private static final int PORTAL_SWITCH = -1;
    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private CityRepository cityRepository;
    private CategoryRepository categoryRepository;
    private CategoryOffersInPolandRepository categoryOffersInPolandRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.categoryCityOffersMapper);
    }

    List<CategoryStatisticsInPolandDto> getCategoryStatisticsInPoland(String cityName) {
        List<CategoryOffersInPoland> offers = categoryOffersInPolandRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(cityName)
                .orElse(null));

        if(cityName.equals("all cities")){
            return null;
        }
        else if (offers.isEmpty()) {
            return mapToDto(scrapCategoryStatisticsInPoland(cityName));
        } else {
            return mapToDto(offers);
        }
    }

    private <T> List<CategoryStatisticsInPolandDto> mapToDto(final List<T> offers) {
        return offers.stream()
                .map(categoryOffer -> modelMapper.map(categoryOffer, CategoryStatisticsInPolandDto.class))
                .collect(Collectors.toList());
    }

    private List<CategoryOffersInPoland> scrapCategoryStatisticsInPoland(String cityName) {
        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);
        List<Category> categories = categoryRepository.findAll();
        List<CategoryOffersInPoland> categoryOffers = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findCityByName(cityNameUTF8);

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");

            if (category.getPracujId() != PLACEHOLDER_OFFER) {
                String pracujUrl = UrlBuilder.pracujBuildUrlForCategory(cityNameASCII, categoryName, category.getPracujId());
                categoryOffers.add(new CategoryOffersInPoland(category, cityOptional.orElse(null), LocalDate.now(), requestCreator.scrapPracujOffers(pracujUrl), PORTAL_SWITCH));
            } else {
                String indeedUrl = UrlBuilder.indeedBuildUrlForCategoryForCity(cityNameUTF8, categoryName);

                try {
                    categoryOffers.add(new CategoryOffersInPoland(category, cityOptional.orElse(null), LocalDate.now(), PORTAL_SWITCH, requestCreator.scrapIndeedOffers(indeedUrl)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return new ArrayList<>(cityOptional
                .filter(ignoredCity -> !categoryOffersInPolandRepository.existsFirstByDateAndCity(LocalDate.now(), ignoredCity))
                .map(ignoredCity -> categoryOffers
                        .stream()
                        .map(category -> categoryOffersInPolandRepository.save(category))
                        .collect(Collectors.toList()))
                .orElse(categoryOffers));
    }
}
