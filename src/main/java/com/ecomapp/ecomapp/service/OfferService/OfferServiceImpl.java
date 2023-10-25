package com.ecomapp.ecomapp.service.OfferService;
import com.ecomapp.ecomapp.model.Offer;
import com.ecomapp.ecomapp.repository.OfferRepository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Offer getOfferById(Long id) {
        return offerRepository.findById(id).orElse(null);
    }

    @Override
    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer updateOffer(Long id, Offer offer) {
        offer.setId(id);
        return offerRepository.save(offer);
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
