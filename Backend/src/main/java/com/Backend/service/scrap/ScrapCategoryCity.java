package com.Backend.service.scrap;

import com.Backend.dto.CategoryDto;
import com.Backend.entity.Category;
import com.Backend.entity.City;
import com.Backend.entity.offers.CategoryCityOffers;
import com.Backend.repository.CategoryRepository;
import com.Backend.repository.CityRepository;
import com.Backend.repository.offers.CategoryCityOffersRepository;
import com.Backend.service.RequestService;
import com.Backend.service.UrlBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScrapCategoryCity {

    private ModelMapper modelMapper;
    private RequestService requestService;
    private CategoryRepository categoryRepository;

    private CityRepository cityRepository;
    private CategoryCityOffersRepository categoryCityOffersRepository;

    public ScrapCategoryCity(ModelMapper modelMapper, RequestService requestService, CategoryRepository categoryRepository,
                             CategoryCityOffersRepository categoryCityOffersRepository, CityRepository cityRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(categoryMapping);
        this.requestService = Objects.requireNonNull(requestService);
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
        this.categoryCityOffersRepository = Objects.requireNonNull(categoryCityOffersRepository);
        this.cityRepository = Objects.requireNonNull(cityRepository);
    }

    private PropertyMap<CategoryCityOffers, CategoryDto> categoryMapping = new PropertyMap<CategoryCityOffers, CategoryDto>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
            map().setId(source.getCategory().getId());
        }
    };

    public List<CategoryDto> getCategoryStatistics(String city){

        List<CategoryCityOffers> list = categoryCityOffersRepository.findByDateAndCity(LocalDate.now(), cityRepository.findCityByName(city));

        if(list.isEmpty()){
            return scrapCategoryStatistics(city);
        } else {
            return list.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        }
    }

    public List<CategoryDto> scrapCategoryStatistics(String cityName) {
        String selectedCityUTF8 = cityName.toLowerCase();
        String selectedCityASCII = requestService.removePolishSigns(selectedCityUTF8);
        List<Category> categories = categoryRepository.findAll();
        List<CategoryCityOffers> categoriesOffers = new ArrayList<>();
        City city = cityRepository.findCityByName(selectedCityUTF8);
        UrlBuilder urlBuilder = new UrlBuilder();

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");
            String pracujDynamicURL = urlBuilder.pracujBuildUrlForCategory(selectedCityASCII, categoryName, category.getPracujId());
            categoriesOffers.add(new CategoryCityOffers(category, city, LocalDate.now(), requestService.scrapPracujOffers(pracujDynamicURL)));
        });

        return categoriesOffers
                .stream()
                .map(categoryOffer -> modelMapper.map(categoryCityOffersRepository.save(categoryOffer), CategoryDto.class))
                .collect(Collectors.toList());
    }
}
