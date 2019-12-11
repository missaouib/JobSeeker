package com.Backend.service;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CityDto;
import com.Backend.dto.CountryDto;
import com.Backend.entity.offers.CategoryCityOffers;
import com.Backend.entity.offers.TechnologyCityOffers;
import com.Backend.entity.offers.TechnologyCountryOffers;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public PropertyMap<CategoryCityOffers, CategoryDto> categoryOffersMapper = new PropertyMap<CategoryCityOffers, CategoryDto>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
            map().setId(source.getCategory().getId());
        }
    };

    public PropertyMap<TechnologyCityOffers, CityDto> cityOffersMapper = new PropertyMap<TechnologyCityOffers, CityDto>() {
        protected void configure() {
            map().setName(source.getCity().getName());
            map().setPopulation(source.getCity().getPopulation());
            map().setArea(source.getCity().getArea());
            map().setDensity(source.getCity().getDensity());
            using(cityOffersTotalConverter).map(map().getTotal());
        }
    };

    public PropertyMap<TechnologyCountryOffers, CountryDto> countryOffersMapper = new PropertyMap<TechnologyCountryOffers, CountryDto>() {
        protected void configure() {
            map().setName(source.getCountry().getName());
            map().setPopulation(source.getCountry().getPopulation());
            map().setArea(source.getCountry().getArea());
            map().setDensity(source.getCountry().getDensity());
            map().setId(source.getCountry().getId());
            using(countryOffersPer100kConverter).map(map().getPer100k());
        }
    };

    public Converter<Double, Double> countryOffersPer100kConverter = context -> {
        TechnologyCountryOffers country = (TechnologyCountryOffers) context.getParent().getSource();
        return (Math.round(country.getLinkedin() * 1.0 / (country.getCountry().getPopulation() * 1.0 / 100000) * 100.0) / 100.0);
    };

    public Converter<Integer, Integer> cityOffersTotalConverter = context -> {
        TechnologyCityOffers cityOffer = (TechnologyCityOffers) context.getParent().getSource();
        return cityOffer.getLinkedin() + cityOffer.getPracuj() + cityOffer.getNoFluffJobs() + cityOffer.getJustJoinIT();
    };

}
