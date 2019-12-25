package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyCityOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyCityOffersRepository extends JpaRepository<TechnologyCityOffers, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);
    List<TechnologyCityOffers> findByDateAndTechnology(LocalDate date, Technology technology);
    List<TechnologyCityOffers> findByDate(LocalDate date);
}
