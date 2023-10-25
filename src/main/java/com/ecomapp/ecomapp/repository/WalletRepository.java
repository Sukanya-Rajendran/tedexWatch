package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.Wallet;
import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet,Long> {
    Wallet findByUser(User user);

}
