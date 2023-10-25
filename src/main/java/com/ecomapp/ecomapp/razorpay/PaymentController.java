package com.ecomapp.ecomapp.razorpay;


import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private RazorPayService RazorPayService;

    @Autowired
    UserService userService;

    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private  String keySecret;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("ajith here ");
        return authentication.getName();

    }
    @GetMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam ("total") String total, Model model, HttpServletRequest request) {
        System.out.println(total + "==================================it is working");
        User user = userService.findByUsername(getCurrentUsername());
        System.out.println(user +"====================================");

        float total2 = Float.parseFloat(total);
        System.out.println("total amount from calling checkout          " + total2 );



        try {
            // Call the Razorpay service to create an order
            String RazorPayorderId = RazorPayService.createOrder((int) total2, "INR");
            System.out.println(RazorPayorderId + "+++++++++++++++++++++++++++++++++++");
            // Store the orderId and total in the session or model for further processing
            HttpSession session = request.getSession();
            session.setAttribute("orderId", RazorPayorderId);
            session.setAttribute("totalprice", total2*100);
            session.setAttribute ( "Email", user.getEmail() );
            session.setAttribute ( "UserName", user.getFirstName() );


            // Return a success response
            return ResponseEntity.ok(RazorPayorderId);
        } catch (RazorpayException e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during Razorpay integration");
        }
    }





    @GetMapping("/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmPayment(@RequestParam ("orderId") String orderId, HttpSession session) {
        System.out.println(orderId+"--------------------------------its an confirm payment");
        try {
            System.out.println (keyId );
            System.out.println ("order"+orderId );
            String price  = session.getAttribute("totalprice").toString();
            System.out.println("price from confirmation " + price);

            System.out.println(price +"after making float");




            System.out.println(price         + "     total");

            JSONObject responseJson = new JSONObject();
            responseJson.put("status", "success");
            responseJson.put("orderId", orderId);
            responseJson.put("amount", price  );
            responseJson.put("key", keyId);
            responseJson.put("UserName",session.getAttribute ( "UserName" ).toString());
            responseJson.put("email",session.getAttribute ( "Email" ).toString());
            System.out.println("came here "+ responseJson);
            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            // Handle exception and return error response
            JSONObject errorJson = new JSONObject();
            errorJson.put("status", "error");
            errorJson.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorJson.toString());
        }
    }
}

//
