package com.ecomapp.ecomapp.service.WalletService;

import com.ecomapp.ecomapp.model.Wallet;
import com.ecomapp.ecomapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet findById(Long id) {
        return walletRepository.findById(id).orElse(null);
    }

    @Override
    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public void creditWallet(Wallet wallet, double amount) {
        if (wallet != null) {
            double currentBalance = wallet.getBalance();
            wallet.setBalance(currentBalance + amount);
            saveWallet(wallet);
        }
    }

    // Implement other wallet-related methods here

}
