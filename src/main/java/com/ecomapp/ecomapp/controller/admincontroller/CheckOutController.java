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

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    @GetMapping("/all")
    public String allCheckouts(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

//        System.out.println(prinicipal.getName());
        List<Address> addresses = addressService.getNonDeleteAddressByCustomer(user);
        for (Address userad : addresses) {
            System.out.println(userad);
        }
        List<Cart> avaliablecartitem = cartRepository.findAll();
        System.out.println(avaliablecartitem.get(0).getProduct().getImages().get(0) + "---------------");
//        Address defaultAddress = addrm essService.getDefaultAddress(principal.getName());
        int total = 0;
        for (Cart item : avaliablecartitem) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }

//
//
//
//  model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("total",total);
        model.addAttribute("avaliablecartitem", avaliablecartitem);
        model.addAttribute("total", cartService.getTotalPrice(principal.getName()));


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

}






//
//    public Address getDefaultAddress(String email) {
//        Address defaultAddress = addressRepository.findDefaultAddress(email);
//        if (defaultAddress == null) {
//            throw new GlobalExceptionHandler.DefaultAddressNotFoundException("Default address not found for user: " + email);
//        }
//        return defaultAddress;
//    }


//    @PostMapping("/saveorder")
//    public String saveorder(@RequestParam( "addressid") UUID addressId,
//
//                            @RequestParam("paymentMethod") Payment paymentmethod) {
//        System.out.println(addressId +"this is addrress id");
//
//        System.out.println( paymentmethod + "this is paymentmethod");
//        User user = userService.findByUsername(getCurrentUsername());
//        Cart cart = user.getCart();
//        UUID id =user.getCart().getId();
//        List<Cart> avaliablecartitem = cartRepository.findAll();
//        List<Product> newproduct = new ArrayList<>();
//        for(Cart carts : avaliablecartitem){
//            newproduct.add(carts.getProduct());
//        }
//        Order savedOrder = orderService.saveOrder(addressId, paymentmethod, user,newproduct);
//        return "redirect:/order";
//
////    }
//@PostMapping("/saveaddress")
//public String saveAddress(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Address address,
//                          @RequestParam(value = "setDefault", required = false) boolean setDefault) {
//    Optional<User> user = Optional.ofNullable(userService.findByUsername(getCurrentUsername()));
//
//    if (user.isPresent()) {
//        User existingUser = user.get();
//        address.setUser(existingUser);
//
//        if (setDefault) {
//            // Set this address as the default address
//            address.setDefaultAddress(true);
//
//            // Clear the default flag for other addresses
//            addressService.clearDefaultAddresses(existingUser, address.getId());
//        }
//
//        addressService.save(address);
//        model.addAttribute("user", user);
//        redirectAttributes.addFlashAttribute("success", "Successfully added");
//        return "redirect:/all";
//    } else {
//        redirectAttributes.addFlashAttribute("error", "User not found");
//        return "redirect:/saveaddress";
//    }
//}

//    }
//    @PostMapping("/setDefaultAddress")
//    public String setDefaultAddress(@RequestParam("addressId") UUID addressId, Principal principal) {
//        String email = principal.getName();
//        addressService.setDefaultAddress(email, addressId);
//        return "redirect:/checkout";
//    }


//
//    @GetMapping("/defaultaddress")
//    public String checkoutPage(Model model, @ModelAttribute("currentUser") User currentUser) {
//        // Get the default address for the user
//        Address defaultAddress = addressService.getDefaultAddressForUser(currentUser);
//
//        model.addAttribute("defaultAddress", defaultAddress);
//
//        // Other checkout page logic
//        // ...
//
//        return "/user/CheckOut";
//    }
    @GetMapping("/deleteAddressAndStayOnCheckout/{addressId}")
    public String deleteAddressAndStayOnCheckout(@PathVariable("addressId") String addressId, Model model) {
        addressService.disableAddress(UUID.fromString(addressId));

        // Add any necessary data to the model for the checkout page

        return "/user/CheckOut";
    }
}






