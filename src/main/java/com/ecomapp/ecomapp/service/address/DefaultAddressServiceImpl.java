package com.ecomapp.ecomapp.service.address;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultAddressServiceImpl implements DefaultAddressService {
    @Autowired
    private AddressRepository addressRepository;

//    @Override
    public Address findDefaultAddressByUser(User user) {
        return addressRepository.findDefaultAddressByUser(user);
    }


    @Override
    public void setDefaultAddress(User user, UUID addressId) {
        List<Address> userAddresses = addressRepository.findByUser(user);

        for (Address address : userAddresses) {
            if (address.getId().equals(addressId)) {
                address.setDefaultAddress(true);
            } else {
                address.setDefaultAddress(false);
            }
            addressRepository.save(address);
        }
    }

    @Override
    public void setDefaultAddress(Address address) {
        // Ensure that the provided address is not null and belongs to the user
        if (address != null) {
            // First, get the user associated with the address
            User user = address.getUser();

            // Then, retrieve all addresses for the user
            List<Address> userAddresses = addressRepository.findByUser(user);

            for (Address userAddress : userAddresses) {
                if (userAddress.getId().equals(address.getId())) {
                    userAddress.setDefaultAddress(true);
                } else {
                    userAddress.setDefaultAddress(false);
                }
                // Save each address to update the default flag
                addressRepository.save(userAddress);
            }
        }
    }
}




