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



    @GetMapping("/details")
    public String userProfile(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                List<Address> userAddress = addressService.getNonDeleteAddressByCustomer(user);
                int randomNumber = (int) (Math.random() * 9000) + 1000;
                model.addAttribute("address", userAddress);
                model.addAttribute("randomNumber", randomNumber);
                model.addAttribute("user", user);

                return "user/NewProfile";
            }
        }

        return "redirect:/login";
    }

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

    @GetMapping("/edit/{addressId}")
    public String editAddress(@PathVariable UUID addressId, Model model) {
        Address address = addressService.findById(addressId).orElse(null);
        model.addAttribute("address", address);
        return "/user/update-address";
    }

}


