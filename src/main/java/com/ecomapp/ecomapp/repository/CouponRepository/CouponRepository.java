package com.ecomapp.ecomapp.repository.CouponRepository;

import com.ecomapp.ecomapp.model.Coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional <Coupon> findByCode(String couponCode);


    boolean existsByCode(String code);



    Coupon getCouponByCode(String code);



//    Coupon discountbycode(String couponcode);
//    Optional<Coupon> findByCouponCode(String code);
}
