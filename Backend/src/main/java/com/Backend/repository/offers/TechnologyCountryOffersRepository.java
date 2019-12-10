package com.Backend.repository.offers;

import com.Backend.entity.Technology;
import com.Backend.entity.offers.TechnologyCountryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyCountryOffersRepository extends JpaRepository<TechnologyCountryOffers, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);
    List<TechnologyCountryOffers> findByDateAndTechnology(LocalDate date, Technology technology);
}