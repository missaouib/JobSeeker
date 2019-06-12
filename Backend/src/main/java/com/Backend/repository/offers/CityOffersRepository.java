package com.Backend.repository.offers;

import com.Backend.entity.Technology;
import com.Backend.entity.offers.CityOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CityOffersRepository extends JpaRepository<CityOffers, Long> {
    Optional<CityOffers> findFirstByDateAndTechnology(LocalDate date, Technology technology);
}
