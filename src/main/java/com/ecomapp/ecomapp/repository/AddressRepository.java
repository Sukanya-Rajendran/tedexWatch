package com.ecomapp.ecomapp.repository;


import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByUser(User user);

    List<Address> findByIsDeletedFalse();


// Arrays findByUserEmail(String email);

//
//    Address findFirstByUserAndDefaultAddressIsTrue(User user);
//
//    // Custom query to find the default address for a user by email
//    @Query("SELECT a FROM Address a WHERE a.user.email = :email AND a.defaultAddress = true")
//    Address findDefaultAddress(@Param("email") String email);
//
//    // Custom query to update non-default addresses to not be default
//    @Modifying
//    @Query("UPDATE Address a SET a.defaultAddress = false WHERE a.user.email = :email AND a.id != :addressId")
//    void updateNonDefaultAddresses(@Param("email") String email, @Param("addressId") UUID addressId);
//    @Modifying
//    @Query("UPDATE Address a SET a.defaultAddress = false WHERE a.user = :user AND a.id != :id")
//    void clearDefaultAddresses(@Param("user") User user, @Param("id") UUID id);







        @Query("SELECT a FROM Address a WHERE a.user = :user AND a.defaultAddress = true")
        Address findDefaultAddressByUser(User user);
    }








