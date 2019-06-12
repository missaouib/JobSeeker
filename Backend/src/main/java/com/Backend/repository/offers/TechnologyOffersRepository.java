package com.Backend.repository.offers;

import com.Backend.entity.City;
import com.Backend.entity.offers.TechnologyOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TechnologyOffersRepository extends JpaRepository<TechnologyOffers, Long> {
    Optional<TechnologyOffers> findFirstByDateAndCity(LocalDate date, City city);
}
