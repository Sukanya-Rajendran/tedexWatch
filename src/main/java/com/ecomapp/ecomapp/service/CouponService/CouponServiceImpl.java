package com.ecomapp.ecomapp.service.CouponService;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.repository.CouponRepository.CouponRepository;
import com.ecomapp.ecomapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponServiceImpl implements CouponService {


    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        if (coupon != null) {
            // Handle the 'active' field
            coupon.setActive(coupon.isActive() == null ? true : coupon.isActive());
        }
        return coupon;
    }


    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Long id, Coupon coupon) {
        Coupon existingCoupon = couponRepository.findById(id).orElse(null);
        if (existingCoupon != null) {
            coupon.setActive(existingCoupon.isActive());
        }
        return couponRepository.save(coupon);
    }


    @Override
    public void deleteCoupon(Long id) {

        couponRepository.deleteById(id);
    }
}

//    @Override
//
//    public boolean validateCoupon(String couponCode, String email) {
//        // Retrieve the user by email
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            // User not found, cannot apply the coupon
//            return false;
//        }
//
//        // Retrieve the coupon by its code
//        Coupon coupon = couponRepository.findByCode(couponCode);
//
//        if (coupon == null) {
//            // Coupon with the provided code does not exist
//            return false;
//        }
//
//        // Check if the coupon is associated with the user, is active, and not expired
//        if (!coupon.getUser().equals(user) || !coupon.isActive() || isCouponExpired(coupon)) {
//            return false;
//        }
//
//        return true; // Coupon is valid and can be applied
//    }
//
//
//    @Override
//    public Cart getCartById(UUID cartId) {
//        return cartRepository.findById(cartId).orElse(null);
//    }
//}

//    @Override
//    public Cart applyCouponToCart(UUID cartId, Coupon coupon) {
//        Cart cart = cartRepository.findById(cartId).orElse(null);
//
//        if (cart != null && coupon != null) {
//            // Check if the coupon is applicable to the products in the cart
//            if (isCouponApplicableToCart(coupon, cart)) {
//                // Apply the coupon discount to the cart
//                double discountAmount = calculateCouponDiscount(coupon, cart);
//                cart.setDiscount(discountAmount);
//                cartRepository.save(cart);
//            }
//        }
//
//        return cart;
//    }
//
//    public boolean isValidCoupon(String couponCode) {
//        // Logic to check if a coupon is valid (e.g., exists and is not expired)
//        Coupon coupon = couponRepository.findByCode(couponCode);
//        if (coupon != null && !isCouponExpired(coupon)) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean isCouponExpired(Coupon coupon) {
//        Date expirationDate = coupon.getExpirationDate();
//        if (expirationDate != null) {
//            return expirationDate.before(new Date());
//        }
//        return false;
//    }

//   /* private double calculateCouponDiscount(Coupon coupon, Cart cart) {
//        // You can implement your logic to calculate the discount amount based on the coupon and cart details.
//        // For example, you can calculate a percentage discount based on the coupon's discount field.
//        double discountPercentage = coupon.getDiscount() / 100.0;
//        double cartTotal = cart.getTotalAmount(); // Assuming you have a method to calculate the cart total.
//        double discountAmount = cartTotal * discountPercentage;
//        return discountAmount;
//    }
//
//
//    private boolean isCouponApplicableToCart(Coupon coupon, Cart cart) {
//        // You can implement your logic to check if the coupon is applicable to the products in the cart.
//        // For example, check if the products in the cart match the conditions of the coupon.
//        // You can compare product IDs, categories, or other criteria.
//        // Return true if applicable, false otherwise.
//        return true; // Implement your logic here
//    }
//
//    //new one
//    @Override
//    public Coupon getCouponByCode(String couponCode) {
//        return couponRepository.findByCode(couponCode);
//    }
//
//
//
//    private boolean isCouponExpired(Coupon coupon) {
//        Date currentDate = new Date();
//        return coupon.getExpirationPeriod() != null && coupon.getExpirationPeriod().before(currentDate);
//    }
//
//}
//*/