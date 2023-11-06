package com.ecomapp.ecomapp.service.CouponService;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.repository.CouponRepository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.ecomapp.ecomapp.model.Coupon;

import java.util.List;
import java.util.UUID;

public interface CouponService {

    List<Coupon> getAllCoupons();

    Coupon getCouponById(Long id);

    Coupon createCoupon(Coupon coupon);

    Coupon updateCoupon(Long id, Coupon coupon);

    void deleteCoupon(Long id);

    boolean validateCouponCode(String couponcode);

    boolean isCouponcodeExsitingOrNot(String couponcode);



    Coupon getCouponByCouponCode(String Couponcode);

//    Coupon getdiscountfromtotal(String couponcode);

//    boolean validateCoupon(String couponCode, String email);
//
//    Coupon getCouponByCode(String couponCode);
//
//    Cart getCartById(UUID cartId);

//    Cart applyCouponToCart(UUID cartId, Coupon coupon);

//    public boolean isValidCoupon(String couponCode) ;
//        // Logic to check if a coupon is valid (e.g., exists and is not expired)
//
//
//    public Cart applyCouponToCart(Long cartId, String couponCode);      // Logic to apply a coupon to a cart
//
//    double getDiscountPercentage(String couponCode);
//}
//
}
