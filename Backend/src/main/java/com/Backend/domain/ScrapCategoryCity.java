package com.Backend.domain;

import com.Backend.infrastructure.dto.CategoryStatisticsInPolandDto;
import com.Backend.infrastructure.entity.Category;
import com.Backend.infrastructure.entity.CategoryCityOffers;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.repository.CategoryCityOffersRepository;
import com.Backend.infrastructure.repository.CategoryRepository;
import com.Backend.infrastructure.repository.CityRepository;
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
class ScrapCategoryCity {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private CityRepository cityRepository;
    private CategoryRepository categoryRepository;
    private CategoryCityOffersRepository categoryCityOffersRepository;

    @PostConstruct
    public void AddMapper() {
        this.modelMapper.addMappings(DtoMapper.categoryCityOffersMapper);
    }

    List<CategoryStatisticsInPolandDto> loadCategoryStatisticsInPoland(String cityName) {
        List<CategoryCityOffers> listOffers = categoryCityOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(cityName).orElse(null));

        if (listOffers.isEmpty()) {
            return scrapCategoryStatisticsInPoland(cityName);
        } else {
            return listOffers
                    .stream()
                    .map(category -> modelMapper.map(category, CategoryStatisticsInPolandDto.class))
                    .collect(Collectors.toList());
        }
    }

    private List<CategoryStatisticsInPolandDto> scrapCategoryStatisticsInPoland(String cityName) {
        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);
        List<Category> categories = categoryRepository.findAll();
        List<CategoryCityOffers> categoriesOffers = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findCityByName(cityNameUTF8);

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");

            if (category.getPracujId() != 0) {
                String pracujUrl = UrlBuilder.pracujBuildUrlForCategory(cityNameASCII, categoryName, category.getPracujId());
                categoriesOffers.add(new CategoryCityOffers(category, cityOptional.orElse(null), LocalDate.now(), requestCreator.scrapPracujOffers(pracujUrl), 0));
            } else {
                String indeedUrl = UrlBuilder.indeedBuildUrlForCategoryForCity(cityNameUTF8, categoryName);
                try {
                    categoriesOffers.add(new CategoryCityOffers(category, cityOptional.orElse(null), LocalDate.now(), 0, requestCreator.scrapIndeedOffers(indeedUrl)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        return cityOptional
                .filter(ignoredCity -> !categoryCityOffersRepository.existsFirstByDateAndCity(LocalDate.now(), ignoredCity))
                .map(ignoredCity -> categoriesOffers.stream().map(category -> modelMapper.map(categoryCityOffersRepository.save(category), CategoryStatisticsInPolandDto.class)).collect(Collectors.toList()))
                .orElseGet(() -> categoriesOffers.stream().map(category -> modelMapper.map(category, CategoryStatisticsInPolandDto.class)).collect(Collectors.toList()));
    }
}
