package com.Backend.service;

import com.Backend.dto.TechnologyDto;
import com.Backend.entity.offers.CityOffers;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    private ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private PropertyMap<CityOffers, TechnologyDto> cityOffersMapper = new PropertyMap<CityOffers, TechnologyDto>() {
        protected void configure() {
            map().setName(source.getTechnology().getName());
            map().setType(source.getTechnology().getType());
            map().setId(source.getTechnology().getId());
            using(totalConverter).map(map().getTotal());
        }
    };

    private Converter<Integer, Integer> totalConverter = context -> {
        CityOffers cityOffer = (CityOffers) context.getParent().getSource();
        return cityOffer.getLinkedin() + cityOffer.getPracuj() + cityOffer.getNoFluffJobs() + cityOffer.getJustJoinIT();
    };

}
