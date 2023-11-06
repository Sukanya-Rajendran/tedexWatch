package com.ecomapp.ecomapp.service;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserDto userDto);
    List<User> listUsers();

    void deleteUserById(long id);

    User findByUsername(String username);

    void updateUser(Long userId, UserDto user) throws Exception;
    public void registerNewCustomer(UserDto userDto);

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException;


    public User getByResetPasswordToken(String token);

    public void updatePassword(User user, String newPassword);

    public String findreferalcodebyuser(User user);

    String findReferralCodeByUser(String email);



    void addAmountToReferedUser(String referralCode);


    User findByEmailOrPhone(String email, String phoneNumber);

    void save(User referrer);



//    String getReferralCodeForUser(User user);
    String getReferralCodeForUser(Long userId);

    User findByReferralCode(String referralCode);
}





