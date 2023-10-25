package com.ecomapp.ecomapp.repository.OfferRepository;

import com.ecomapp.ecomapp.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByReferralCode(String referralCode);

    // Custom query to find active offers
    List<Offer> findByActiveTrue();
}
    // You can add custom query methods here if needed

