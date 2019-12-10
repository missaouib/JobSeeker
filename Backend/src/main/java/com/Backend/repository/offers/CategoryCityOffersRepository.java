package com.Backend.repository.offers;

import com.Backend.entity.City;
import com.Backend.entity.offers.CategoryCityOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CategoryCityOffersRepository extends JpaRepository<CategoryCityOffers, Long> {
    Boolean existsFirstByDateAndCity(LocalDate date, City city);
    List<CategoryCityOffers> findByDateAndCity(LocalDate date, City city);
}
