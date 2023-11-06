package com.ecomapp.ecomapp.service.OfferService;
import com.ecomapp.ecomapp.model.Offer;
import com.ecomapp.ecomapp.repository.OfferRepository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;


    @Override
    public List<Offer> getAllOffers() {
       return offerRepository.findAll();
    }

    @Override
    public void createOffer(Offer offer) {
        Offer newOffer = new Offer();
        newOffer.setOfferPercentage(offer.getOfferPercentage());
        newOffer.setOfferType(offer.getOfferType());
        newOffer.setActive(true);
        newOffer.setDescription(offer.getDescription());
        newOffer.setExpireDate(offer.getExpireDate());
        offerRepository.save(newOffer);

    }
}
