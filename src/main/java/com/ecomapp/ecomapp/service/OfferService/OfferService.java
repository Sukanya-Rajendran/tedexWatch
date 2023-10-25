package com.ecomapp.ecomapp.service.OfferService;
import com.ecomapp.ecomapp.model.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> getAllOffers();

    Offer getOfferById(Long id);

    Offer createOffer(Offer offer);

    Offer updateOffer(Long id, Offer offer);

    void deleteOffer(Long id);
}
