package com.Backend.core.service;

import com.Backend.infrastructure.dto.*;
import com.Backend.infrastructure.entity.CategoryOffersInPoland;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import com.Backend.infrastructure.entity.TechnologyOffersInWorld;
import org.modelmapper.PropertyMap;

public class DtoMapper {

    public static PropertyMap<CategoryOffersInPoland, CategoryStatisticsInPolandDto> categoryCityOffersMapper = new PropertyMap<CategoryOffersInPoland, CategoryStatisticsInPolandDto>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
        }
    };

    public static PropertyMap<TechnologyOffersInPoland, JobsOffersInPolandDto> technologyCityOffersMapper = new PropertyMap<TechnologyOffersInPoland, JobsOffersInPolandDto>() {
        protected void configure() {
            map().setName(source.getCity().getName());
            map().setPopulation(source.getCity().getPopulation());
            map().setArea(source.getCity().getArea());
            map().setDensity(source.getCity().getDensity());
        }
    };

    public static PropertyMap<TechnologyOffersInWorld, JobsOffersInWorldDto> technologyCountryOffersMapper = new PropertyMap<TechnologyOffersInWorld, JobsOffersInWorldDto>() {
        protected void configure() {
            map().setName(source.getCountry().getName());
            map().setPopulation(source.getCountry().getPopulation());
            map().setArea(source.getCountry().getArea());
            map().setDensity(source.getCountry().getDensity());
        }
    };

    public static PropertyMap<TechnologyOffersInPoland, TechnologyStatisticsInPolandDto> technologyStatisticsInPolandMapper = new PropertyMap<TechnologyOffersInPoland, TechnologyStatisticsInPolandDto>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
        }
    };

    public static PropertyMap<TechnologyOffersInWorld, TechnologyStatisticsInWorldDto> technologyStatisticsInWorldMapper = new PropertyMap<TechnologyOffersInWorld, TechnologyStatisticsInWorldDto>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
        }
    };

}
