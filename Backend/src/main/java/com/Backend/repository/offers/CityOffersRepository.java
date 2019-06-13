package com.Backend.repository.offers;

import com.Backend.entity.Technology;
import com.Backend.entity.offers.CityOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CityOffersRepository extends JpaRepository<CityOffers, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);
}
