//package com.ecomapp.ecomapp.model;//package com.ecomapp.ecomapp.model;//package com.ecomapp.ecomapp.model;
////
//import com.ecomapp.ecomapp.model.BaseEntity;
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class Order extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Type(type = "org.hibernate.type.UUIDCharType")
//    private UUID id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Enumerated(EnumType.STRING)
//    private Status Status;
//
//    @Enumerated(EnumType.STRING)
//    private Payment payment;
//
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
//    private List<Cart> carts = new ArrayList<>();
//
//    // Other order-related fields and methods
//}
//
