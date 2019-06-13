package com.Backend.repository.offers;

import com.Backend.entity.Technology;
import com.Backend.entity.offers.CountryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CountryOffersRepository extends JpaRepository<CountryOffers, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);
}
