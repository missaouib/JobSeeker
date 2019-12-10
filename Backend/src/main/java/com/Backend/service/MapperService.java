package com.Backend.service;

import com.Backend.dto.CategoryDto;
import com.Backend.dto.CountryDto;
import com.Backend.dto.TechnologyDto;
import com.Backend.entity.offers.CategoryOffers;
import com.Backend.entity.offers.CityOffers;
import com.Backend.entity.offers.CountryOffers;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public PropertyMap<CategoryOffers, CategoryDto> categoryMapper = new PropertyMap<CategoryOffers, CategoryDto>() {
        protected void configure() {
            map().setPolishName(source.getCategory().getPolishName());
            map().setEnglishName(source.getCategory().getEnglishName());
            map().setId(source.getCategory().getId());
        }
    };

    public PropertyMap<CityOffers, TechnologyDto> cityOffersMapper = new PropertyMap<CityOffers, TechnologyDto>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
            map().setId(source.getTechnology().getId());
            using(cityOffersTotalConverter).map(map().getTotal());
        }
    };

    public PropertyMap<CountryOffers, CountryDto> countryMapping = new PropertyMap<CountryOffers, CountryDto>() {
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
        CountryOffers country = (CountryOffers) context.getParent().getSource();
        return (Math.round(country.getLinkedin() * 1.0 / (country.getCountry().getPopulation() * 1.0 / 100000) * 100.0) / 100.0);
    };

    public Converter<Integer, Integer> cityOffersTotalConverter = context -> {
        CityOffers cityOffer = (CityOffers) context.getParent().getSource();
        return cityOffer.getLinkedin() + cityOffer.getPracuj() + cityOffer.getNoFluffJobs() + cityOffer.getJustJoinIT();
    };

}
