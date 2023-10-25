package com.ecomapp.ecomapp.controller.ProfileControlller;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.UserServiceImpl;
import com.ecomapp.ecomapp.service.address.AddressService;
import com.ecomapp.ecomapp.service.address.DefaultAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private DefaultAddressService defaultAddressService;
    @Autowired
    private UserServiceImpl userserviceimpl;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

//    @GetMapping("/details")
//    public String UserProfile(Principal principal, Model model) {
//        User user = userService.findByUsername(principal.getName());
//        List<Address> userAddress = addressService.getNonDeleteAddressByCustomer(user);
//        System.out.println(userAddress+"--------------");
////        int randomNumber = (int) (Math.random() * 9000) + 1000;
//
////        Address defaultAddress = defaultAddressService.findDefaultAddressByUser(user);
//       String referralCode = userserviceimpl.findReferralCodeByUser(user.getEmail());
//
//        model.addAttribute("address", userAddress);
////        model.addAttribute("defaultAddress", defaultAddress);
////         Add the random number to the model
//        model.addAttribute("referralCode", referralCode);
//
//        model.addAttribute("user", user);
//        return "/user/NewProfile";
//    }


    // test code for details
//    @GetMapping("/details")
//    public String UserProfile(Principal principal, Model model) {
//        User user = userService.findByUsername(principal.getName());
//        List<Address> userAddress = addressService.getNonDeleteAddressByCustomer(user);
//
//        // Create a map to store unique IDs for addresses
//        Map<Address, String> addressUniqueIds = new HashMap<>();
//
//        for (Address address : userAddress) {
//            // Generate a unique ID for each address (you can use UUID or any other method)
//            String uniqueId = UUID.randomUUID().toString();
//            addressUniqueIds.put(address, uniqueId);
//        }
//
//        // Retrieve the referral code
//        String referralCode = userserviceimpl.findReferralCodeByUser(user.getEmail);
//
//        model.addAttribute("addressUniqueIds", addressUniqueIds);
//        model.addAttribute("referralCode", referralCode);
//        model.addAttribute("user", user);
//
//        return "user/NewProfile";
//    }



    @GetMapping("/details")
    public String UserProfile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<Address> userAddress = addressService.getNonDeleteAddressByCustomer(user);
        Address defaultAddress = defaultAddressService.findDefaultAddressByUser(user);

        // Generate a random number (e.g., between 1000 and 9999)
        int randomNumber = (int) (Math.random() * 9000) + 1000;

        model.addAttribute("address", userAddress);
        model.addAttribute("defaultAddress", defaultAddress);

        // Add the random number to the model
        model.addAttribute("randomNumber", randomNumber);

        model.addAttribute("user", user);
        return "/user/NewProfile";
    }

//
//    @PostMapping("/setDefaultAddress/{addressId}")
//    public String setDefaultAddress(@PathVariable("addressId") UUID addressId, Principal principal) {
//        System.out.println(addressId+"==========================this is default");
//        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
//        User user = userService.findByUsername(principal.getName());
//        Address address = addressService.findAddressById(addressId);
//
//        if (address != null && address.getUser().equals(user)) {
//            // Update the default address for the user
//            defaultAddressService.setDefaultAddress(address);
//        }
//        return "redirect:/details";
//    }
//


//    @GetMapping("/details")
//    public String UserProfile(Principal principal, Model model) {
//        User user = userService.findByUsername(principal.getName());
//        List<Address> userAddress = addressService.getNonDeleteAddressByCustomer(user);
//        System.out.println(user);
//        for (Address Customer : userAddress) {
//            System.out.println(Customer);
//        }
//        model.addAttribute("address", userAddress);
//        model.addAttribute("user", user);
//        return "/user/NewProfile";
//    }

    @GetMapping
    public String showprofilepage() {
        return "/user/ProfileOne";
    }


    @GetMapping("/deleteAddress/{addressId}")
    public String deleteaddress(@PathVariable("addressId") String addressId) {
        addressService.disableAddress(UUID.fromString(addressId));
        return "redirect:/profile/details";
    }

    @PostMapping("/update/{addressId}")
    public String updateAddress(@PathVariable String addressId, Address updatedAddress) {

        System.out.println(addressId + "address id");
        System.out.println(updatedAddress + "updated address");

        addressService.updateAddress(UUID.fromString(addressId), updatedAddress);
        return "redirect:/profile/details";
    }




    @GetMapping("/edit/{addressId}")  // Specify a different URL for the GET request
    public String editAddress(@PathVariable UUID addressId, Model model) {
        Address address = addressService.findById(addressId).orElse(null);
        model.addAttribute("address", address);
        return "/user/update-address";
    }

}


