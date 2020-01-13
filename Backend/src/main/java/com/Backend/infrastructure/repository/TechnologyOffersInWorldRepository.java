package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.Country;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInWorld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyOffersInWorldRepository extends JpaRepository<TechnologyOffersInWorld, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInWorld> findByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInWorld> findByDateAndCountry(LocalDate date, Country country);

    List<TechnologyOffersInWorld> findByDate(LocalDate date);

    @Query("SELECT c.name, c.population, c.area, c.density, " +
            "sum(case when c.id = o.country.id then o.linkedin else 0 end) as linkedin, " +
            "sum(case when c.id = o.country.id then o.indeed else 0 end) as indeed " +
            "FROM TechnologyOffersInWorld o, Country c " +
            "WHERE (o.date) = :date " +
            "GROUP BY c.name, c.population, c.area, c.density")
    List<Object[]> findAllTechnlogiesInItJobOffersInWorld(@Param("date")LocalDate date);

    @Query("SELECT t.name, t.type, " +
            "sum(case when t.id = o.technology.id then o.linkedin else 0 end) as linkedin, " +
            "sum(case when t.id = o.technology.id then o.indeed else 0 end) as indeed " +
            "FROM TechnologyOffersInWorld o, Technology t " +
            "WHERE (o.date) = :date " +
            "GROUP BY t.name, t.type")
    List<Object[]> findAllTechnologiesInTechnologyStatsInWorld(@Param("date")LocalDate date);
}