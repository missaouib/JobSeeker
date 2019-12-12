//package com.Backend.service.scrap;
//
//import com.Backend.dto.CityDto;
//import com.Backend.entity.City;
//import com.Backend.entity.Technology;
//import com.Backend.entity.offers.TechnologyCityOffers;
//import com.Backend.repository.CityRepository;
//import com.Backend.repository.TechnologyRepository;
//import com.Backend.repository.offers.TechnologyCityOffersRepository;
//import com.Backend.service.MapperService;
//import com.Backend.service.RequestService;
//import com.Backend.service.UrlBuilder;
//import org.modelmapper.ModelMapper;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//public class TemporaryScrapTechnology {
//
//    private ModelMapper modelMapper;
//    private MapperService mapperService;
//    private RequestService requestService;
//    private TechnologyRepository technologyRepository;
//    private TechnologyCityOffersRepository technologyCityOffersRepository;
//    private CityRepository cityRepository;
//
//    public TemporaryScrapTechnology(ModelMapper modelMapper, RequestService requestService, TechnologyRepository technologyRepository,
//                               CityRepository cityRepository, MapperService mapperService,
//                               TechnologyCityOffersRepository technologyCityOffersRepository) {
//        this.modelMapper = Objects.requireNonNull(modelMapper);
//        this.mapperService = Objects.requireNonNull(mapperService);
//        this.modelMapper.addMappings(mapperService.technologyCityOffersMapper);
//        //this.modelMapper.addConverter(mapperService.cityOffersTotalConverter);
//
//        this.requestService = Objects.requireNonNull(requestService);
//        this.cityRepository = Objects.requireNonNull(cityRepository);
//        this.technologyRepository = Objects.requireNonNull(technologyRepository);
//        this.technologyCityOffersRepository = Objects.requireNonNull(technologyCityOffersRepository);
//    }
//
//    public List<CityDto> scrapTechnologyStatisticsForCities(String cityName) {
//
//        String cityNameUTF8 = cityName.toLowerCase();
//        String cityNameASCII = requestService.removePolishSigns(cityNameUTF8).toLowerCase();
//        List<Technology> technologies = technologyRepository.findAll();
//        City city = cityRepository.findCityByName(cityNameUTF8);
//        List<TechnologyCityOffers> technologyCityOffers = new ArrayList<>();
//        UrlBuilder urlBuilder = new UrlBuilder();
//
//        technologies.forEach(technology -> {
//
//            String technologyName = technology.getName().toLowerCase();
//
//            String linkedinDynamicURL = urlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, cityNameASCII);
//            String indeedDynamicURL = urlBuilder.indeedBuildUrlLForCity(technologyName, cityNameASCII);
//            String pracujDynamicURL = urlBuilder.pracujBuildUrlForCity(technologyName, cityNameASCII);
//            String noFluffJobsDynamicURL = urlBuilder.noFluffJobsBuildUrlForCity(technologyName, cityNameASCII);
//
//            TechnologyCityOffers offer = new TechnologyCityOffers(city, technology, LocalDate.now());
//
//            try {
//                offer.setIndeed(requestService.scrapIndeedOffers(indeedDynamicURL));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            offer.setLinkedin(requestService.scrapLinkedinOffers(linkedinDynamicURL));
//            offer.setPracuj(requestService.scrapPracujOffers(pracujDynamicURL));
//            offer.setNoFluffJobs(requestService.scrapNoFluffJobsOffers(noFluffJobsDynamicURL));
//            offer.setJustJoinIT(requestService.scrapJustJoin(city, technology));
//
//            technologyCityOffers.add(offer);
//        });
//
//        return technologyCityOffers
//                .stream()
//                .map(cityOffer -> modelMapper.map(technologyCityOffersRepository.save(cityOffer), CityDto.class))
//                .peek(cityDto -> cityDto.setTotal(cityDto.getLinkedin() + cityDto.getPracuj() + cityDto.getNoFluffJobs() + cityDto.getJustJoinIT()))
//                .peek(cityDto -> cityDto.setPer100k(Math.round(cityDto.getTotal() * 1.0 / (cityDto.getPopulation() * 1.0 / 100000) * 100.0) / 100.0))
//                .collect(Collectors.toList());
//    }
//
//}


//public List<CountryDto> scrapCountriesStatisticsForTechnology(String countryName) {
//
//        String countryNameUTF8 = countryName.toLowerCase();
//        List<TechnologyCountryOffers> countriesOffers = new ArrayList<>();
//        List<Technology> technologies = technologyRepository.findAll();
//        Country country = countryRepository.findCountryByName(countryNameUTF8);
//
//        technologies.forEach(technology -> {
//
//        String technologyName = technology.getName().toLowerCase();
//        String linkedinUrl = UrlBuilder.linkedinBuildUrlForCityAndCountry(technologyName, countryNameUTF8);
//        String indeedUrl = UrlBuilder.indeedBuildUrlForCountry(technologyName, country.getCode());
//
//        TechnologyCountryOffers technologyCountryOffers = new TechnologyCountryOffers(country, technology, LocalDate.now());
//
//        try {
//        technologyCountryOffers.setIndeed(requestCreator.scrapIndeedOffers(indeedUrl));
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//
//        technologyCountryOffers.setLinkedin(requestCreator.scrapLinkedinOffers(linkedinUrl));
//
//        countriesOffers.add(technologyCountryOffers);
//        });
//
//        return countriesOffers
//        .stream()
//        .map(countryOffer -> modelMapper.map(technologyCountryOffersRepository.save(countryOffer), CountryDto.class))
//        .collect(Collectors.toList());
//        }