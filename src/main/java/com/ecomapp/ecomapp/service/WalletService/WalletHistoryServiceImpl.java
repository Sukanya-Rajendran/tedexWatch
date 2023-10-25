package com.ecomapp.ecomapp.service.WalletService;

import com.ecomapp.ecomapp.model.Wallet;
import com.ecomapp.ecomapp.model.WalletHistory;
import com.ecomapp.ecomapp.repository.WalletHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletHistoryServiceImpl  implements  WalletHistoryService{
    @Autowired
  private   WalletHistoryRepository walletHistoryRepository;
    @Override
    public void saveWalletHistory(WalletHistory walletHistory) {
        walletHistoryRepository.save(walletHistory);
    }

    @Override
    public List<WalletHistory> getWalletHistoryByWallet(Wallet wallet) {
        return walletHistoryRepository.findByWallet(wallet);
    }


}
