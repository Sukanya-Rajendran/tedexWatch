package com.ecomapp.ecomapp.service.WalletService;

import com.ecomapp.ecomapp.model.Wallet;

public interface WalletService {

    Wallet findById(Long id);

    Wallet saveWallet(Wallet wallet);

    void creditWallet(Wallet wallet, double amount);

    // Add other wallet-related methods as needed
}
