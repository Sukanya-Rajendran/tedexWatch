package com.ecomapp.ecomapp.service.WalletService;

import com.ecomapp.ecomapp.model.Wallet;
import com.ecomapp.ecomapp.model.WalletHistory;

import java.util.List;

public interface WalletHistoryService {
    void saveWalletHistory(WalletHistory referralHistory);

    List<WalletHistory> getWalletHistoryByWallet(Wallet wallet);
}
