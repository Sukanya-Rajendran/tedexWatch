package com.ecomapp.ecomapp.controller.admincontroller.OrderManagment;

import com.ecomapp.ecomapp.model.CheckOut;
import com.ecomapp.ecomapp.model.Status;
import com.ecomapp.ecomapp.repository.CheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class OrderManagementController {
    @Autowired
    CheckOutRepository checkOutRepository;

    @GetMapping("/ordermanage")
    public String ordermanage(Model model, Authentication authentication) {
        List<CheckOut> checkout = checkOutRepository.findAll();
        model.addAttribute("orders", checkout);
        return "/admin/OrderManagement";

    }


    //    @PostMapping("/order/{orderId}/updateStatus")
//    public String updateOrderStatus(@PathVariable String orderId, @RequestParam String newStatus) {
//        System.out.println(orderId);
//        System.out.println(newStatus);
//        CheckOut checkOut = checkOutRepository.findById(UUID.fromString(orderId)).orElse(null);
//        checkOut.setStatus(Status.valueOf(newStatus));
//        checkOutRepository.save(checkOut);
//
//        return "redirect:/ordermanage";
//    }


// ...


    @PostMapping("/order/{orderId}/updateStatus")
    @ResponseBody
    public ResponseEntity<String> updateOrderStatus(@PathVariable UUID orderId, @RequestParam String newStatus) {
        System.out.println(orderId+"------------it is order id");
        System.out.println(newStatus+"----------------------it is newStatus");
        try {
            // Find the order by ID
            CheckOut order = checkOutRepository.findById(orderId).orElse(null);

            if (order != null) {
                // Update the order status
                order.setStatus(Status.valueOf(newStatus));
                checkOutRepository.save(order);

                return ResponseEntity.ok("Order status updated successfully");
            } else {
                return ResponseEntity.notFound().build(); // Return a 404 response if the order is not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating order status");
        }
    }
}

//    CheckOut checkOut = checkOutRepository.findById(orderId).orElse(null);
//
//        if (checkOut != null) {
//                checkOut.setStatus(Status.valueOf(newStatus));
//                checkOutRepository.save(checkOut);
//                }
// return "redirect:/user/orderdetails/" + orderId;

