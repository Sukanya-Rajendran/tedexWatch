package com.ecomapp.ecomapp.service.address;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;

import java.util.UUID;

public interface DefaultAddressService {
    Address findDefaultAddressByUser(User user);

    void setDefaultAddress(User user,UUID id);

    void setDefaultAddress(Address address);
}

