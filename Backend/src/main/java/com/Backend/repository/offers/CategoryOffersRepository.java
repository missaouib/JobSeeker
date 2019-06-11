package com.Backend.repository.offers;

import com.Backend.entity.offers.CategoryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryOffersRepository extends JpaRepository<CategoryOffers, Long> {
}
