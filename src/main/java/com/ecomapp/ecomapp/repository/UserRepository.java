package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    List<User> findByFirstNameContaining(String firstName);
    List<User> findByLastNameContaining(String lastName);
    List<User> findByEmailContaining(String email);

    public User findByResetPasswordToken(String token);


    @Query("SELECT u.referralCode FROM User u WHERE u.email = :email")
    String findReferralCodeByEmail(@Param("email") String email);

    User findByReferralCode(String referralCode);
}



