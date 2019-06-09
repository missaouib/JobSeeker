package com.Backend.repository.offers;

import com.Backend.entity.offers.CategoryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryOffersRepository extends JpaRepository<CategoryOffers, Long> {
}
