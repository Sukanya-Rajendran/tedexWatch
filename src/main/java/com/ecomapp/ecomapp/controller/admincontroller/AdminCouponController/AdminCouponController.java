package com.ecomapp.ecomapp.controller.admincontroller.AdminCouponController;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.service.CouponService.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/coupons")
public class AdminCouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/")
    public String listCoupons(Model model) {
        model.addAttribute("coupons", couponService.getAllCoupons());
        return "admin/coupon/list";
    }

    @GetMapping("/create")
    public String showCouponForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "admin/coupon/form";
    }

    @PostMapping("/create")
    public String createCoupon(@ModelAttribute Coupon coupon) {
        couponService.createCoupon(coupon);
        return "redirect:/admin/coupons/";
    }

    @GetMapping("/edit/{id}")
    public String editCouponForm(@PathVariable Long id, Model model) {
        Coupon coupon = couponService.getCouponById(id);
        model.addAttribute("coupon", coupon);
        return "admin/coupon/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCoupon(@PathVariable Long id, @ModelAttribute Coupon coupon) {
        // Update the 'active' field if necessary
        Coupon existingCoupon = couponService.getCouponById(id);
        coupon.setActive(existingCoupon.isActive()); // Preserve the current 'active' status
        couponService.updateCoupon(id, coupon);
        return "redirect:/admin/coupons/";
    }


    @GetMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return "redirect:/admin/coupons/";
    }
}
    //new method
//
//    @PostMapping("/applyCoupon")
//    public String applyCouponToCart(
//            @RequestParam("cartId") UUID cartId,
//            @RequestParam("couponCode") String couponCode,
//            @RequestParam("totalAmount") double totalAmount,
//            RedirectAttributes redirectAttributes) {
//        System.out.println(cartId+"-------------------------");
//        System.out.println(couponCode+"-------------------------------------------");
//        System.out.println(totalAmount+"-------------------it is the total amount");
//
//        // Retrieve the cart and coupon
//        Cart cart = couponService.getCartById(cartId);
//        Coupon coupon = couponService.getCouponByCode(couponCode);
//
//        if (cart != null && coupon != null) {
//            // Check if the cart already has a coupon applied
//            if (cart.getCoupon() != null) {
//                // Handle the case where the user can only use one coupon
//                redirectAttributes.addAttribute("error", "Only one coupon allowed.");
//            } else {
//                // Calculate the discounted total
//                double discount = coupon.getDiscount();
//                double discountedTotal = totalAmount * (1 - discount);
//
//                // Update the cart's discount and total amount
//                cart.setDiscount(discount);
//                cart.setTotalAmount((int) discountedTotal);
//                cart.setCoupon(coupon);
//
//                // Save the updated cart
//                cart = couponService.applyCouponToCart(cartId, coupon);
//
//                if (cart != null) {
//                    // Successfully applied coupon, you can redirect or return a response
//                    return "redirect:/cart/" + cartId;
//                }
//            }
//        }
//
//        // Handle errors appropriately (e.g., redirect with an error message)
//        redirectAttributes.addAttribute("error", "Invalid coupon.");
//        return "redirect:/cart/" + cartId;
//    }
//}
