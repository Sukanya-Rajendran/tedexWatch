//package com.ecomapp.ecomapp.model;
//
//
//
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class CustomerCoupon {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;
//
//}
