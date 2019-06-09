package com.Backend.repository;

import com.Backend.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
