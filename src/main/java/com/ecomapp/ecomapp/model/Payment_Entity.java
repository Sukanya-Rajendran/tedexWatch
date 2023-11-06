package com.ecomapp.ecomapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Payment_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private CheckOut checkOut;

    private String paymentMethod; // e.g., "credit_card", "paypal", etc.

    private double amount;

    private LocalDateTime paymentDate;

    private boolean isPaid;
}

//    public Payment_Entity() {
//        this.paymentDate = LocalDateTime.now();
//        this.isPaid = false;
//    }
//}

    // Getters and setters, or use Lombok annotations for getters and setters

//    public Long getId() {
//        return id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public CheckOut getOrder() {
//        return order;
//    }
//
//    public void setOrder(OrderEntity order) {
//        this.order = order;
//    }
//
//    public String getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public LocalDateTime getPaymentDate() {
//        return paymentDate;
//    }
//
//    public void setPaymentDate(LocalDateTime paymentDate) {
//        this.paymentDate = paymentDate;
//    }
//
//    public boolean isPaid() {
//        return isPaid;
//    }
//
//    public void setPaid(boolean paid) {
//        isPaid = paid;
//    }
//}
