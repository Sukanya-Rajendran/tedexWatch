package com.ecomapp.ecomapp.razorpay;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorPayService {


    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    public String createOrder(int amount, String currency) throws RazorpayException {
        try {
            System.out.println("hai lasr razorpay -------------------------------");
            RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount*100);
            orderRequest.put("currency", currency);
            Order order = razorpayClient.orders.create(orderRequest);
            System.out.println("from razorpay service class" + order);


            return order.get("id");
        } catch (Exception e) {
            throw new RazorpayException("Failed to create order: " + e.getMessage());
        }
    }
}
