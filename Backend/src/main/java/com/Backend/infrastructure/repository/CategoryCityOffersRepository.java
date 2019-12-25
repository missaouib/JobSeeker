package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.CategoryCityOffers;
import com.Backend.infrastructure.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CategoryCityOffersRepository extends JpaRepository<CategoryCityOffers, Long> {
    Boolean existsFirstByDateAndCity(LocalDate date, City city);
    List<CategoryCityOffers> findByDateAndCity(LocalDate date, City city);
    List<CategoryCityOffers> findByDate(LocalDate date);
}
