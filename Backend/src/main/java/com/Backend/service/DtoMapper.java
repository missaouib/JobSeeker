package com.Backend.service;

import com.Backend.infrastructure.dto.CategoryDto;
import com.Backend.infrastructure.dto.CityDto;
import com.Backend.infrastructure.dto.CountryDto;
import com.Backend.infrastructure.entity.CategoryCityOffers;
import com.Backend.infrastructure.entity.TechnologyCityOffers;
import com.Backend.infrastructure.entity.TechnologyCountryOffers;
import org.modelmapper.PropertyMap;

public class DtoMapper {

    public static PropertyMap<CategoryCityOffers, CategoryDto> categoryCityOffersMapper = new PropertyMap<CategoryCityOffers, CategoryDto>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
            map().setId(source.getCategory().getId());
        }
    };

    public static PropertyMap<TechnologyCityOffers, CityDto> technologyCityOffersMapper = new PropertyMap<TechnologyCityOffers, CityDto>() {
        protected void configure() {
            map().setName(source.getCity().getName());
            map().setPopulation(source.getCity().getPopulation());
            map().setArea(source.getCity().getArea());
            map().setDensity(source.getCity().getDensity());
        }
    };

    public static PropertyMap<TechnologyCountryOffers, CountryDto> technologyCountryOffersMapper = new PropertyMap<TechnologyCountryOffers, CountryDto>() {
        protected void configure() {
            map().setName(source.getCountry().getName());
            map().setPopulation(source.getCountry().getPopulation());
            map().setArea(source.getCountry().getArea());
            map().setDensity(source.getCountry().getDensity());
            map().setId(source.getCountry().getId());
        }
    };

}
