package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.CategoryOffersInPoland;
import com.Backend.infrastructure.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CategoryOffersInPolandRepository extends JpaRepository<CategoryOffersInPoland, Long> {
    Boolean existsFirstByDateAndCity(LocalDate date, City city);

    List<CategoryOffersInPoland> findByDateAndCity(LocalDate date, City city);

    List<CategoryOffersInPoland> findByDate(LocalDate date);
}
