package com.ecomapp.ecomapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private String amount;

    private LocalDate transaction_date;

    @Enumerated(EnumType.STRING)
    private Transaction transaction;

    private Wallet_Method walletMethod;
}
