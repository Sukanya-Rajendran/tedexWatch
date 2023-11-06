package com.ecomapp.ecomapp.controller.Offer;

import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.model.Offer;
import com.ecomapp.ecomapp.repository.categoryrepo.CategoryRepo;
import com.ecomapp.ecomapp.service.OfferService.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/offers")
public class OfferManagementController {
    @Autowired
    private OfferService offerService;


    @Autowired
    CategoryRepo categoryRepo;

    @GetMapping("/offer")
    public String listOfOffers(Model model) {
        List<Offer> offers = offerService.getAllOffers(); // Modify this based on your service
        model.addAttribute("offers", offers);
        return "/admin/Offer/manage_offers";
    }
    @GetMapping("/typeOfOffer")
    public String showOfferForm(Model model) {
        model.addAttribute("offer", new Offer());
        List<Category> categoryList = categoryRepo.findCategoryByIsAvailableTrue();
        System.out.println(categoryList+"categoryList123435983490");
        model.addAttribute("categories",categoryList);
        return "admin/Offer/create_offer";
    }

    @PostMapping("/typeOfOffer")
    public String createOffer(@ModelAttribute Offer offer) {
        System.out.println(offer+"23782184921941294721748129747````````````````````````````````````````````````````````````");
        offerService.createOffer(offer);
        return "redirect:/offers/offer";
    }

//    @PostMapping("/typeOfOffer")
//    public String createCoupon(@ModelAttribute Offer offer, @RequestParam("offerType") String offerType) {
//        System.out.println(offer+"this is offer");
//        System.out.println(offerType+"this is offer type 232424");
//        offer.setOfferType(offerType); // Set the offer type in the Offer object
//        offerService.createOffer(offer);
//        System.out.println(offer + "it is offer 1233535325325");
//        return "redirect:/offers/offer";
//    }





}
