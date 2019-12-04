package com.Backend.repository;

import com.Backend.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT o.name FROM Country o")
    List<String> findAllNames();

    @Query(value = "SELECT o FROM Country o WHERE o.code IS NOT null")
    List<Country> findAllCountriesWithCode();

    Optional<Country> findCountryByName(String country);
}
