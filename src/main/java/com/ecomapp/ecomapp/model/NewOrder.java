//package com.ecomapp.ecomapp.model;
//
//
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//
//@Entity
//@Builder
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class NewOrder extends BaseEntity {
//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
//        @GenericGenerator(name = "uuid2", strategy = "uuid2")
//        @Type(type = "org.hibernate.type.UUIDCharType")
//        private UUID id;
//
//        @ManyToOne
//        @JoinColumn(name = "cart_id")
//        private Cart cart;
//
//        private LocalDateTime orderDateTime;
//
//        @Enumerated(EnumType.STRING)
//        private Status Status;
//
//        @Enumerated(EnumType.STRING)
//        private Payment payment;
//
//        // Other fields and methods specific to NewOrder
//    }
//
//
