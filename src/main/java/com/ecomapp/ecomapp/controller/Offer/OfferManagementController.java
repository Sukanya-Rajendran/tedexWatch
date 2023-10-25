package com.ecomapp.ecomapp.controller.Offer;

import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.model.Offer;
import com.ecomapp.ecomapp.service.OfferService.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferManagementController {
    @Autowired
    private OfferService offerService;


    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/{id}")
    public Offer getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id);
    }


    @PutMapping("/{id}")
    public Offer updateOffer(@PathVariable Long id, @RequestBody Offer offer) {
        return offerService.updateOffer(id, offer);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }


    //    @GetMapping("/list")
//    public String listReferralCodes(Model model) {
//        List<ReferralCode> referralCodes = referralCodeService.getAllReferralCodes();
//        model.addAttribute("referralCodes", referralCodes);
//        return "list_referral_codes"; // Create a Thymeleaf template for listing referral codes
//    }
    @GetMapping("/list")
    public String listOffers(Model model) {
        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
//        List<Offer> offers = offerService.getAllOffers();
        model.addAttribute("offers", offerService.getAllOffers());
        return "admin/Offer/manage_Offers"; // Create a Thymeleaf template for listing offers
    }

    @GetMapping("/create")
    public String createOfferForm(Model model) {
        model.addAttribute("newOffer", new Offer());
        return "admin/Offer/create_Offer"; // Create a Thymeleaf template for creating offers
    }

    @PostMapping("/create")
    public String createOffer(@ModelAttribute Offer offer) {
        System.out.println(offer + "==================");
        offerService.createOffer(offer);
        return "redirect:/offers/list";
    }

}

//    @GetMapping("/manage")
//    public String manageOffers(Model model) {
//        System.out.println("hioneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        List<Offer> offers = offerService.getAllOffers();
//        model.addAttribute("offers", offers);
//        model.addAttribute("newOffer", new Offer()); // An empty Offer object for the form
//        return "admin/Offers/manage_Offers"; // Create a Thymeleaf template for managing offers
//    }
//}
