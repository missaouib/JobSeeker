package com.Backend.service;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CityDto;
import com.Backend.dto.CountryDto;
import com.Backend.entity.offers.CategoryCityOffers;
import com.Backend.entity.offers.TechnologyCityOffers;
import com.Backend.entity.offers.TechnologyCountryOffers;
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
