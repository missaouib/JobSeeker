package com.Backend.service;

import com.Backend.infrastructure.dto.CategoryStatisticsInPolandDto;
import com.Backend.infrastructure.dto.JobsOffersInPolandDto;
import com.Backend.infrastructure.dto.JobsOffersInWorldDto;
import com.Backend.infrastructure.entity.CategoryCityOffers;
import com.Backend.infrastructure.entity.TechnologyCityOffers;
import com.Backend.infrastructure.entity.TechnologyCountryOffers;
import org.modelmapper.PropertyMap;

public class DtoMapper {

    public static PropertyMap<CategoryCityOffers, CategoryStatisticsInPolandDto> categoryCityOffersMapper = new PropertyMap<CategoryCityOffers, CategoryStatisticsInPolandDto>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
        }
    };

    public static PropertyMap<TechnologyCityOffers, JobsOffersInPolandDto> technologyCityOffersMapper = new PropertyMap<TechnologyCityOffers, JobsOffersInPolandDto>() {
        protected void configure() {
            map().setName(source.getCity().getName());
            map().setPopulation(source.getCity().getPopulation());
            map().setArea(source.getCity().getArea());
            map().setDensity(source.getCity().getDensity());
        }
    };

    public static PropertyMap<TechnologyCountryOffers, JobsOffersInWorldDto> technologyCountryOffersMapper = new PropertyMap<TechnologyCountryOffers, JobsOffersInWorldDto>() {
        protected void configure() {
            map().setName(source.getCountry().getName());
            map().setPopulation(source.getCountry().getPopulation());
            map().setArea(source.getCountry().getArea());
            map().setDensity(source.getCountry().getDensity());
        }
    };

}
