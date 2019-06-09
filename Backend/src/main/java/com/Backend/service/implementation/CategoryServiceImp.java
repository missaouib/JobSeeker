package com.Backend.service.implementation;

import com.Backend.dto.CategoryDto;
import com.Backend.entity.Category;
import com.Backend.entity.offers.CategoryOffers;
import com.Backend.repository.CategoryRepository;
import com.Backend.repository.offers.CategoryOffersRepository;
import com.Backend.service.CategoryService;
import com.Backend.service.ScrapJobService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private ModelMapper modelMapper;
    private ScrapJobService scrapJobService;
    private CategoryRepository categoryRepository;
    private CategoryOffersRepository categoryOffersRepository;

    public CategoryServiceImp(ModelMapper modelMapper, ScrapJobService scrapJobService, CategoryRepository categoryRepository, CategoryOffersRepository categoryOffersRepository) {
        this.modelMapper = Objects.requireNonNull(modelMapper);
        this.modelMapper.addMappings(categoryMapping);
        this.scrapJobService = Objects.requireNonNull(scrapJobService);
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
        this.categoryOffersRepository = Objects.requireNonNull(categoryOffersRepository);
    }

    private PropertyMap<CategoryOffers, CategoryDto> categoryMapping = new PropertyMap<>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
        }
    };

    public List<CategoryDto> scrapCategoryStatistics(ModelMap city) {
        String selectedCityUTF8 = city.get("city").toString().toLowerCase();
        String selectedCityASCII = scrapJobService.removePolishSigns(selectedCityUTF8);
        List<Category> categories = categoryRepository.findAll();
        List<CategoryOffers> categoriesOffers = new ArrayList<>();

        categories.forEach(category -> {
            String categoryName = category.getPolishName().toLowerCase().replaceAll("/ ", "");
            WebClient pracujURL = WebClient.create("https://www.pracuj.pl/praca/" + selectedCityASCII + ";wp/" + categoryName + ";cc," + category.getPracujId());
            categoriesOffers.add(new CategoryOffers(category, LocalDate.now(), scrapJobService.getPracujOffers(pracujURL)));
        });
        return categoriesOffers.stream().map(category -> modelMapper.map(categoryOffersRepository.save(category), CategoryDto.class)).collect(Collectors.toList());
    }

    public List<CategoryDto> getLastDataFromDB() {
        return null;
    }
}
