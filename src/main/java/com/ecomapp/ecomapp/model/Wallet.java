package com.ecomapp.ecomapp.model;

import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
        @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
        @ToString.Exclude
    List<WalletHistory> history = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


// Add any other fields and methods you need for your wallet entity.

    // Getters and setters
}
