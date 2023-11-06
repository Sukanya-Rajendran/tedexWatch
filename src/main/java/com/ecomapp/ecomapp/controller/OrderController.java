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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @GetMapping("/save/{addressId}/{paymentStatus}")
    public ResponseEntity<String> saveOrder(
            @PathVariable("addressId") String addressId,
            @PathVariable("paymentStatus") String paymentmethod,
            Principal principal) throws JsonProcessingException {

        User user = userservice.findByUsername(principal.getName());
        List<Cart> cart =cartRepository.findByUser_id(user.getId());
        System.out.println(cart +"this is my order cart+========================");

        Cart userCart =user.getCart();
        Address address = addressRepository.findById(UUID.fromString(addressId)).orElse(null);

            CheckOut checkOut = new CheckOut();
            checkOut.setAddress(address);
            checkOut.setUser(userCart.getUser());
            checkOut.setProduct(userCart.getProduct());
            checkOut.setStatus(Status.PENDING);
            checkOut.setCount(userCart.getQuantity());

            if (paymentmethod.equals("ONLINE")) {

                // Handle online payment method
                checkOut.setPayment(Payment.ONLINE);
                // Add code to process online payment here
            } else if (paymentmethod.equals("COD")) {
                System.out.println("hhhiii");
                // Handle COD payment method
                checkOut.setPayment(Payment.COD);
                // Add code to process COD payment here
            }

        System.out.println(paymentmethod+"===============payment");

            checkOut.setCreatedAt(LocalDateTime.now());

            checkOutRepository.save(checkOut);
          cartRepository.delete(userCart);

            System.out.println(checkOut);


//        System.out.println(selectedCartIds);
        System.out.println(paymentmethod);
        System.out.println(addressId);

        return ResponseEntity.status(HttpStatus.OK).body("Order saved successfully");
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
        List<CheckOut> checkOut = checkOutRepository.findByUser(user);
        model.addAttribute("order", checkOut);
        System.out.println(checkOut);

        return "user/CartProduct-detail";
    }

    @GetMapping("/viewOrder")
    public String ViewOrderdetailsByeachOrder(){
        return "/user/Order-detail-view";
    }



}




