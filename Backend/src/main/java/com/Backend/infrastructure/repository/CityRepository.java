package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT o FROM City o WHERE lower(o.name) = :name OR o.name = :name")
    City findCityByName(@Param("name") String name);

    @Query(value = "SELECT o.name FROM City o")
    List<String> findAllNames();
}
