package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.Country;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInWorld;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyOffersInWorldRepository extends JpaRepository<TechnologyOffersInWorld, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInWorld> findByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInWorld> findByDateAndCountry(LocalDate date, Country country);

    List<TechnologyOffersInWorld> findByDate(LocalDate date);
}