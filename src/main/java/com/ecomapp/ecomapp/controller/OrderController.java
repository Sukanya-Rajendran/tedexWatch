package com.ecomapp.ecomapp.controller;


import com.ecomapp.ecomapp.model.*;
import com.ecomapp.ecomapp.repository.AddressRepository;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.repository.CheckOutRepository;
import com.ecomapp.ecomapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CheckOutRepository checkOutRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserService userservice;
    @Autowired
    CartRepository cartRepository;


    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/success")
    public String orderpage() {
        return "/user/OrderSuccessPage";
    }

    //    @Autowired
//    ObjectMapper objectMapper;
    @PostMapping("/save")
    public String saveorder(
            @RequestParam("selectedCartIds") List<UUID> selectedCartIds,
            @RequestParam("addressid") String addressId,
            @RequestParam("paymentMethod") String paymentmethod,
            Principal principal) throws JsonProcessingException {
//        List<Cart> cartnew = objectMapper.readValue(cartlist, new TypeReference<List<Cart>>() {
//        }
//        );


        User user = userservice.findByUsername(principal.getName());
        for (int i = 0; i < selectedCartIds.size(); i++) {
            Address address = addressRepository.findById(UUID.fromString(addressId)).orElse(null);
            Cart cart = cartRepository.findById(selectedCartIds.get(i)).orElse(null);
            CheckOut checkOut = new CheckOut();
            checkOut.setAddress(address);
            checkOut.setUser(cart.getUser());
            checkOut.setProduct(cart.getProduct());
            checkOut.setStatus(Status.PENDING);
            checkOut.setCount(cart.getQuantity());
            checkOut.setPayment(Payment.valueOf(paymentmethod));
            checkOut.setCreatedAt(LocalDateTime.now());

            checkOutRepository.save(checkOut);
            cartRepository.delete(cart);


            System.out.println(checkOut);

        }
        System.out.println(selectedCartIds);
        System.out.println(paymentmethod);
        System.out.println(addressId);
        return "redirect:/order/success";
    }

    @GetMapping("/orderdetails")
    public String orderdetails(Principal principal, Model model) {
        User user = userservice.findByUsername(principal.getName());
        List<CheckOut> checkOut = checkOutRepository.findByUser(user);
        model.addAttribute("orders", checkOut);
        return "user/Order-detail";

    }

    @GetMapping("/orderdetails/{orderId}")
    public String viewOrderDetails( UUID id, Model model) {
        User user = userservice.findByUsername(getCurrentUsername());
        // Fetch the order details based on the provided order ID
        List<CheckOut> checkOut = checkOutRepository.findByUser(user);
        model.addAttribute("order", checkOut);
        System.out.println(checkOut);
//        model.addAttribute("products", checkOuts);

        return "user/CartProduct-detail";
    }




}




