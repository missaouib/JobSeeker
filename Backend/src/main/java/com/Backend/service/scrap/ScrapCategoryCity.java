package com.Backend.service.scrap;

import com.Backend.dto.CategoryDto;
import com.Backend.entity.Category;
import com.Backend.entity.City;
import com.Backend.entity.offers.CategoryCityOffers;
import com.Backend.repository.CategoryRepository;
import com.Backend.repository.CityRepository;
import com.Backend.repository.offers.CategoryCityOffersRepository;
import com.Backend.service.DtoMapper;
import com.Backend.service.RequestCreator;
import com.Backend.service.UrlBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
class ScrapCategoryCity {

    private ModelMapper modelMapper;
    private RequestCreator requestCreator;
    private CityRepository cityRepository;
    private CategoryRepository categoryRepository;
    private CategoryCityOffersRepository categoryCityOffersRepository;

    public ScrapCategoryCity(ModelMapper modelMapper, RequestCreator requestCreator, CategoryRepository categoryRepository,
                             CategoryCityOffersRepository categoryCityOffersRepository, CityRepository cityRepository) {

        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(DtoMapper.categoryCityOffersMapper);
        this.requestCreator = Objects.requireNonNull(requestCreator);
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
        this.categoryCityOffersRepository = Objects.requireNonNull(categoryCityOffersRepository);
        this.cityRepository = Objects.requireNonNull(cityRepository);
    }

    public List<CategoryDto> loadCategoryStatisticsInPoland(String city){

        List<CategoryCityOffers> list = categoryCityOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(city));

        if(list.isEmpty()){
            return scrapCategoryStatisticsInPoland(city);
        } else {
            return list.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        }
    }

    public List<CategoryDto> scrapCategoryStatisticsInPoland(String cityName) {
        String cityNameUTF8 = cityName.toLowerCase();
        String cityNameASCII = requestCreator.removePolishSigns(cityNameUTF8);
        List<Category> categories = categoryRepository.findAll();
        List<CategoryCityOffers> categoriesOffers = new ArrayList<>();
        City city = cityRepository.findCityByName(cityNameUTF8);

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");
            String pracujDynamicURL = UrlBuilder.pracujBuildUrlForCategory(cityNameASCII, categoryName, category.getPracujId());
            categoriesOffers.add(new CategoryCityOffers(category, city, LocalDate.now(), requestCreator.scrapPracujOffers(pracujDynamicURL)));
        });

        return categoriesOffers
                .stream()
                .map(categoryOffer -> modelMapper.map(categoryCityOffersRepository.save(categoryOffer), CategoryDto.class))
                .collect(Collectors.toList());
    }
}
