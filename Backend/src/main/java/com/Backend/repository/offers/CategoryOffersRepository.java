package com.Backend.repository.offers;

import com.Backend.entity.City;
import com.Backend.entity.offers.CategoryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CategoryOffersRepository extends JpaRepository<CategoryOffers, Long> {
    Optional<CategoryOffers> findFirstByDateAndCity(LocalDate date, City city);
}
