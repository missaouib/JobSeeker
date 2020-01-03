package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyOffersInPolandRepository extends JpaRepository<TechnologyOffersInPoland, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInPoland> findByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInPoland> findByDateAndCity(LocalDate date, City city);

    List<TechnologyOffersInPoland> findByDate(LocalDate date);
}
