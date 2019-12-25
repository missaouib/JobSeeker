package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyCountryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyCountryOffersRepository extends JpaRepository<TechnologyCountryOffers, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);
    List<TechnologyCountryOffers> findByDateAndTechnology(LocalDate date, Technology technology);
    List<TechnologyCountryOffers> findByDate(LocalDate date);
}