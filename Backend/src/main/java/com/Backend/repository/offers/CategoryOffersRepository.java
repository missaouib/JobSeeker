package com.Backend.repository.offers;

import com.Backend.entity.City;
import com.Backend.entity.offers.CategoryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CategoryOffersRepository extends JpaRepository<CategoryOffers, Long> {
    Boolean existsFirstByDateAndCity(LocalDate date, City city);
    List<CategoryOffers> findByDateAndCity(LocalDate date, City city);
}
