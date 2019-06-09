package com.Backend.repository.offers;

import com.Backend.entity.offers.CountryOffers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryOffersRepository extends JpaRepository<CountryOffers, Long> {
}
