package com.ecomapp.ecomapp.service.OrderService;

import com.ecomapp.ecomapp.model.*;
import com.ecomapp.ecomapp.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl  implements  OrderService {
}

//    @Autowired
//    AddressRepository addressRepository;
//    @Autowired
//    OrderRepository orderRepository;

//
//    @Override
//    public Order saveOrder(UUID addressId, Payment paymentmethod, User user, List<Product> products) {
//        Order neworder = new Order();
//
//        neworder.setOrdered_date(LocalDate.now());
//        Address address = addressRepository.findById(addressId).orElse(null);
//        neworder.setAddress(address);
//        neworder.setPayment(paymentmethod);
//        neworder.setStatus(Status.PENDING);
//         neworder.setProducts(products);
//        LocalDate modifiedDate = neworder.getOrdered_date().plus(7, ChronoUnit.DAYS);
//        neworder.setExpecting_date(modifiedDate);
//        LocalDate shippingdate = neworder.getOrdered_date().plus(3, ChronoUnit.DAYS);
//        neworder.setShipping_date(shippingdate);
//
//        orderRepository.save(neworder);
//
////
////        return neworder;
////    }
//
//    @Override
//
//    public Order createOrder(Order order, Cart cart) {
//        // Save the order using the OrderRepository
//        Order savedOrder = orderRepository.save(order);
//
////        // Delete the cart using the CartService
////        cartService.deleteCartById(cart.getId());
//
//        return savedOrder;
//    }
//}
