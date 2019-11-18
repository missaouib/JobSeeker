package com.Backend.repository;

import com.Backend.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT o.name FROM Country o")
    List<String> findAllNames();
}
