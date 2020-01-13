package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TechnologyOffersInPolandRepository extends JpaRepository<TechnologyOffersInPoland, Long> {
    Boolean existsFirstByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInPoland> findByDateAndTechnology(LocalDate date, Technology technology);

    List<TechnologyOffersInPoland> findByDateAndCity(LocalDate date, City city);

    List<TechnologyOffersInPoland> findByDate(LocalDate date);

    @Query("SELECT c.name, c.population, c.area, c.density, " +
            "sum(case when c.id = o.city.id then o.linkedin else 0 end) as linkedin, " +
            "sum(case when c.id = o.city.id then o.indeed else 0 end) as indeed, " +
            "sum(case when c.id = o.city.id then o.pracuj else 0 end) as pracuj, " +
            "sum(case when c.id = o.city.id then o.noFluffJobs else 0 end) as noFluffJobs, " +
            "sum(case when c.id = o.city.id then o.justJoinIt else 0 end) as JustJoinIt " +
            "FROM TechnologyOffersInPoland o, City c " +
            "WHERE (o.date) = :date " +
            "GROUP BY c.name, c.population, c.area, c.density")
    List<Object[]> findAllTechnlogiesInItJobOffersInPoland(@Param("date")LocalDate date);

    @Query("SELECT t.name, t.type, " +
            "sum(case when t.id = o.technology.id then o.linkedin else 0 end) as linkedin, " +
            "sum(case when t.id = o.technology.id then o.indeed else 0 end) as indeed, " +
            "sum(case when t.id = o.technology.id then o.pracuj else 0 end) as pracuj, " +
            "sum(case when t.id = o.technology.id then o.noFluffJobs else 0 end) as noFluffJobs, " +
            "sum(case when t.id = o.technology.id then o.justJoinIt else 0 end) as JustJoinIt " +
            "FROM TechnologyOffersInPoland o, Technology t " +
            "WHERE (o.date) = :date " +
            "GROUP BY t.name, t.type")
    List<Object[]> findAllTechnologiesInTechnologyStatsInPoland(@Param("date")LocalDate date);
}