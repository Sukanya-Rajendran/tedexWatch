package com.ecomapp.ecomapp.repository.CouponRepository;

import com.ecomapp.ecomapp.model.Coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {


    Coupon findByCode(String couponCode);
}
