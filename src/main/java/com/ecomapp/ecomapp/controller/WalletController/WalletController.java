package com.ecomapp.ecomapp.controller.WalletController;

import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.model.Wallet;
import com.ecomapp.ecomapp.model.WalletHistory;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.repository.WalletRepository;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.WalletService.WalletHistoryService;
import com.ecomapp.ecomapp.service.WalletService.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletHistoryService walletHistoryService;


    @GetMapping("/wallet")
    public String viewWallet(Model model, Principal principal) {
        // Retrieve the user from the logged-in principal
        User user = userService.findByUsername(principal.getName());

        // Get the user's wallet
        Wallet wallet = user.getWallet();

        if (wallet == null) {
            // If the user doesn't have a wallet, create one
            wallet = new Wallet();
            wallet.setBalance(100);
            wallet.setUser(user);
            walletRepository.save(wallet);
        }

        // Add wallet information to the model
        model.addAttribute("wallet", wallet);

        return "/user/wallet"; // Return the HTML template name for displaying the wallet.
    }

    @GetMapping("/wallet/history")
    public String viewWalletHistory(Model model, Principal principal) {
        // Retrieve the user from the logged-in principal
        User user = userService.findByUsername(principal.getName());

        // Get the user's wallet
        Wallet wallet = user.getWallet();

        if (wallet != null) {
            // Retrieve the wallet history for the user's wallet
            List<WalletHistory> walletHistoryList = walletHistoryService.getWalletHistoryByWallet(wallet);

            // Add the wallet history to the model
            model.addAttribute("walletHistoryList", walletHistoryList);
        }

        return "user/wallet"; // Thymeleaf template to display the wallet history
    }



}

//    @PostMapping("/addCredit")
//    public String addCreditToWallet(@RequestParam double amount, Principal principal) {
//        // Retrieve the user from the logged-in principal
//        User user = userService.findByUsername(principal.getName());
//
//        // Get the user's wallet
//        Wallet wallet = user.getWallet();
//
//        // Add the specified amount to the wallet
//        double currentBalance = wallet.getBalance();
//        wallet.setBalance(currentBalance + amount);
//
//        // Save the updated wallet
//        walletService.saveWallet(wallet);
//
//        return "redirect:/wallet"; // Redirect back to the wallet page after adding credit.
//    }
//}
