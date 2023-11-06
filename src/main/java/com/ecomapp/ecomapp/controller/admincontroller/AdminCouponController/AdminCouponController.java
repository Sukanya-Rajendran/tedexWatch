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
