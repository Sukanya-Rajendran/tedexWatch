package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.Wallet;
import com.ecomapp.ecomapp.model.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory,Long> {
    List<WalletHistory> findByTransaction(String transactionType);

    List<WalletHistory> findByWallet(Wallet wallet);
}
