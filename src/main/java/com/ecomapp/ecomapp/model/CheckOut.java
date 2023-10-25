package com.ecomapp.ecomapp.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class CheckOut extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToOne
    @JoinColumn(name="address")
    private Address  Address;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    private int count ;

    @OneToOne
    @JoinColumn(name="product")
    private Product product;

//   @OneToOne
//   @JoinColumn(name="order")
//   private Order order;





    @Enumerated(EnumType.STRING)
    private Payment payment;









//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Status Status;




}