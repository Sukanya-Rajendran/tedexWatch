package com.ecomapp.ecomapp.service.address;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {

    void save(Address address);

    Optional<Address> findById(UUID id);

    List<Address> findByUser(User user);

//    void deleteAddress(Address address);
    void deleteUserAddress(UUID id);

    void disableAddress(UUID id);


    List<Address> getNonDeleteAddressByCustomer(User user);

   Address getAddressById(UUID id);


//    Address editAddress(UUID id, Address updatedAddress);

    Address updateAddress(UUID addressId, Address updatedAddress);

//    Address getDefaultAddressForUser(User currentUser);
//
//    Address getDefaultAddress(String email);
//
//    void setDefaultAddress(String email, UUID addressId);
//
//    void clearDefaultAddresses(User existingUser, UUID id);

    Address findAddressById(UUID addressId);
}

