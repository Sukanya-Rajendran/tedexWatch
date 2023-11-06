package com.ecomapp.ecomapp.service;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.*;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.WalletService.WalletHistoryService;
import com.ecomapp.ecomapp.service.WalletService.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletHistoryService walletHistoryService;

    // This method is used by Spring Security to load a user by their username.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find a user by their email (username).
        User user = userRepository.findByEmail(username);
        if (user == null) {
            // If the user is not found, throw an exception.
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        // Map the user's roles to Spring Security authorities and create a UserDetails object.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }


    // This method maps the user's roles to Spring Security authorities.
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    public String generateRandomReferralCode() {
        // Generate a random alphanumeric referral code (customize as needed)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder referralCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            referralCode.append(characters.charAt(random.nextInt(characters.length())));
        }

        return referralCode.toString();
    }

    // This method is used to save a new user.
    @Override
    public User save(UserDto userDto) {
        // Create a new User object with the provided userDto and encode the password.

        User user = new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(new Role("ROLE_USER")), // Assign a default role to the user.
                false
        );


        // Save the user to the UserRepository.
        return userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        System.out.println(username + "7373493278y6r794 hsdhsadhsgdhgsjhcbsa1111111111111111");
        return userRepository.findByEmail(username);
    }

    public void addAmountToWallet(User user, double amount) {
        Wallet wallet = user.getWallet();
        if (wallet != null) {
            double currentBalance = wallet.getBalance();
            double newBalance = currentBalance + amount;
            wallet.setBalance(newBalance);
        }
    }


    @Override
    @Transactional
    public void updateUser(Long userId, UserDto userDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("not found"));
        System.out.println("in update user , user entity");
        System.out.println(userDto);
        System.out.println(user);
        if (user != null) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
        }
        System.out.println(user);
        userRepository.save(user);
    }


    @Override
    public void registerNewCustomer(UserDto userDto) {
        String referralCode = generateRandomReferralCode();
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
        newUser.setReferralCode(referralCode);
        Wallet wallet = new Wallet();
        wallet.setBalance(0.0); // Set an initial balance

        // Associate the wallet with the user
        newUser.setWallet(wallet);


        newUser.setBlocked(userDto.isBlocked());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setId(userDto.getId());
        if (referralCode != null && !referralCode.isEmpty()) {
            // Find the user with the provided referral code
            User referringUser = userRepository.findByReferralCode(referralCode);
            if (referringUser != null) {
                newUser.setReferringUser(referringUser);
            }
        }


        userRepository.save(newUser);
        System.out.println("saved");

    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println(user + "----------------------------");
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }


    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }


    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public String findreferalcodebyuser(User user) {
        return null;
    }

    @Override
    public String findReferralCodeByUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getReferralCode();
        }
        return null; // Handle the case where the user with the specified email is not found.
    }

    @Override
    public void addAmountToReferedUser(String referralCode) {
        User referringUser = userRepository.findByReferralCode(referralCode);
//        if (referringUser != null) {
//            // Credit the referring user with 100 Rs
//            Wallet referringUserWallet = referringUser.getWallet();
//
//            referringUserWallet.setBalance(referringUserWallet.getBalance()+100);
//            walletService.saveWallet(referringUserWallet);
//
//        }
        if (referringUser != null) {
            // Credit the referring user with 100 Rs
            Wallet referringUserWallet = referringUser.getWallet();
            double currentBalance = referringUserWallet.getBalance();
            double newBalance = currentBalance + 100.0;
            referringUserWallet.setBalance(newBalance);
            walletService.saveWallet(referringUserWallet);

            // Create a history entry for the referral bonus
            WalletHistory referralHistory = new WalletHistory();
            referralHistory.setWallet(referringUserWallet);
            referralHistory.setAmount("100.0"); // Set the amount for the referral bonus
            referralHistory.setTransaction_date(LocalDate.now());
            referralHistory.setTransaction(Transaction.CREDIT);
            referralHistory.setWalletMethod(Wallet_Method.FROM_REFERRAL);

            walletHistoryService.saveWalletHistory(referralHistory);
        }
    }

    @Override
    public User findByEmailOrPhone(String email, String phoneNumber) {
        return userRepository.findByEmailOrPhone(email, phoneNumber);
    }

    @Override
    public void save(User referrer) {
        userRepository.save(referrer);
    }

//    @Override
//    public String getReferralCodeForUser(User user) {
//        return  userRepository.getReferralCode(user.getId());
//    }

    @Override
    public String getReferralCodeForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getReferralCode();
        } else {
            // Handle the case where the user with the given userId is not found.
            return null;
        }
    }

    @Override
    public User findByReferralCode(String referralCode) {
        return  userRepository.findByReferralCode(referralCode);
    }
}





//it is not working