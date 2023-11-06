package com.ecomapp.ecomapp.controller.admincontroller;





import com.ecomapp.ecomapp.config.OtpConfig.GlobalExceptionHandler;
import com.ecomapp.ecomapp.model.*;
import com.ecomapp.ecomapp.repository.AddressRepository;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.service.CartService.CartService;
import com.ecomapp.ecomapp.service.OrderService.OrderService;
import com.ecomapp.ecomapp.service.ProductService.ProductService;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.address.AddressService;
import com.ecomapp.ecomapp.service.address.DefaultAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;


@Controller
public class CheckOutController {
    @Autowired
    private UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DefaultAddressService defaultAddressService;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    @GetMapping("/all")
    public String allCheckouts(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Address defaultaddress = user.getDefaultAddress();
        System.out.println(defaultaddress+"123465465236532653214521621");




//        System.out.println(prinicipal.getName());
        List<Address> addresses = addressService.getNonDeleteAddressByCustomer(user);
        for (Address userad : addresses) {
            System.out.println(userad);
        }



        List<Cart> cart  = cartRepository.findByUser_id(user.getId());
        System.out.println(cart+"===============================cart id");
        System.out.println(cart.get(0).getProduct().getImages().get(0) + "---------------");
//        Address defaultAddress = addrm essService.getDefaultAddress(principal.getName());
        int total = 0;
        for (Cart item : cart) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        Address defaultAddress = null;
        for (Address address : addresses) {
            if (address.isDefault()) { // Assuming isDefault is a boolean attribute
                defaultAddress = address;
                System.out.println(defaultAddress);
                break; // No need to continue checking once we find the default address
            }
        }


//
//
//
//  model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("total",total);
        model.addAttribute("avaliablecartitem", cart);
        model.addAttribute("total", cartService.getTotalPrice(principal.getName()));
        model.addAttribute("defaultAddress", defaultaddress);

//        System.out.println(defaultAddress+"================================");
        model.addAttribute("address", addresses);
        model.addAttribute("user", user);
        return "/user/CheckOut";
    }

    @PostMapping("/saveaddress")
    public String saveAddress(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Address address) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByUsername(getCurrentUsername()));

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Check if it's the first address for the user; if so, set it as the default
            boolean isFirstAddress = existingUser.getAddresses().isEmpty();
            if (isFirstAddress) {
                address.setDefaultAddress(true);
            }

            address.setUser(existingUser);
            addressService.save(address);
            model.addAttribute("user", existingUser);

            if (isFirstAddress) {
                redirectAttributes.addFlashAttribute("success", "Successfully added as default address");
            } else {
                redirectAttributes.addFlashAttribute("success", "Successfully added");
            }

            return "redirect:/all";
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/saveaddress";
        }
    }

    @GetMapping("/productdetail")
    public String productdetail() {
        return "/user/CartProduct-detail";
    }

    @GetMapping("/deleteAddress/{addressId}")
    public String deleteaddress() {
        return "/user/CheckOut";

}    @GetMapping("/default-address/set/{addressId}")
    public String setDefaultAddress(@PathVariable("addressId") UUID addressId, Principal principal, RedirectAttributes redirectAttributes) {
        defaultAddressService.setDefaultAddressForUser(principal.getName(), addressId);
        redirectAttributes.addFlashAttribute("message", "Address updated as default");
        return "redirect:/all";
    }
    @GetMapping("/deleteAddressAndStayOnCheckout/{addressId}")
    public String deleteAddressAndStayOnCheckout(@PathVariable("addressId") String addressId, Model model) {
        addressService.disableAddress(UUID.fromString(addressId));

        // Add any necessary data to the model for the checkout page

        return "/user/CheckOut";
    }
}






