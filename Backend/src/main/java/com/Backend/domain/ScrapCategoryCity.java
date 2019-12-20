package com.Backend.domain;

import com.Backend.infrastructure.dto.CategoryDto;
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

    public List<CategoryDto> loadCategoryStatisticsInPoland(String cityName) {
        List<CategoryCityOffers> listOffers = categoryCityOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(cityName).orElse(null));

        if (listOffers.isEmpty()) {
            return scrapCategoryStatisticsInPoland(cityName);
        } else {
            return listOffers.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        }
    }

    public List<CategoryDto> scrapCategoryStatisticsInPoland(String cityName) {
        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);
        List<Category> categories = categoryRepository.findAll();
        List<CategoryCityOffers> categoriesOffers = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findCityByName(cityNameUTF8);

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");

            if(category.getPracujId() != 0){
                String pracujDynamicURL = UrlBuilder.pracujBuildUrlForCategory(cityNameASCII, categoryName, category.getPracujId());
                categoriesOffers.add(new CategoryCityOffers(category, cityOptional.orElse(null), LocalDate.now(), requestCreator.scrapPracujOffers(pracujDynamicURL)));
            } else {
                String indeedDynamicURL = UrlBuilder.indeedBuildUrlForCategoryForCity(cityNameUTF8, categoryName);
                categoriesOffers.add(new CategoryCityOffers(category, cityOptional.orElse(null), LocalDate.now(), 0));
            }


        });

        return categoriesOffers
                .stream()
                .map(categoryOffer -> modelMapper.map(categoryCityOffersRepository.save(categoryOffer), CategoryDto.class))
                .collect(Collectors.toList());
    }
}
