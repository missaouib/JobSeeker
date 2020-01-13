package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.CategoryOffersInPoland;
import com.Backend.infrastructure.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CategoryOffersInPolandRepository extends JpaRepository<CategoryOffersInPoland, Long> {
    Boolean existsFirstByDateAndCity(LocalDate date, City city);

    List<CategoryOffersInPoland> findByDateAndCity(LocalDate date, City city);

    List<CategoryOffersInPoland> findByDate(LocalDate date);

    @Query("SELECT c.polishName, c.englishName, " +
            "sum(case when c.id = o.category.id then o.pracuj else 0 end) as pracuj, " +
            "sum(case when c.id = o.category.id then o.indeed else 0 end) as indeed " +
            "FROM CategoryOffersInPoland o, Category c " +
            "WHERE (o.date) = :date " +
            "GROUP BY c.polishName, c.englishName")
    List<Object[]> findAllCategoriesInCategoryStats(@Param("date") LocalDate date);
}
