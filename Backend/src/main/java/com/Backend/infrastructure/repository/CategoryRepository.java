package com.Backend.infrastructure.repository;

import com.Backend.infrastructure.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
