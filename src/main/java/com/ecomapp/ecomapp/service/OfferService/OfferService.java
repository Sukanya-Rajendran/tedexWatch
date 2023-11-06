package com.ecomapp.ecomapp.service.OfferService;
import com.ecomapp.ecomapp.model.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> getAllOffers();

    void createOffer(Offer offer);



}
