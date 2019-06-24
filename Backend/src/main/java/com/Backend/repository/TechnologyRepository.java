package com.Backend.repository;

import com.Backend.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    @Query(value = "SELECT o FROM Technology o WHERE lower(o.name) = :name OR o.name = :name")
    Optional<Technology> findTechnologyByName(@Param("name") String name);

    @Query(value = "SELECT o.name FROM Technology o")
    List<String> findAllNames();
}
