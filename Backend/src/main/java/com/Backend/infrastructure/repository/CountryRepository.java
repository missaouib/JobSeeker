package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT o.name FROM Country o")
    List<String> findAllNames();

    @Query(value = "SELECT o FROM Country o WHERE o.code IS NOT null")
    List<Country> findAllCountriesWithCode();

    @Query(value = "SELECT o FROM Country o WHERE lower(o.name) = :name OR o.name = :name")
    Optional<Country> findCountryByName(@Param("name") String country);
}
