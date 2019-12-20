package com.Backend.domain;

import com.Backend.infrastructure.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class ScrapCategoryCountry {

    //Indeed stuff
    public List<CategoryDto> scrapCategoryStatisticsForCountries(String countryName) {
        return new ArrayList<CategoryDto>();
    }

}
