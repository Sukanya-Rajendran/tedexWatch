package com.ecomapp.ecomapp.service.address;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.AddressRepository;
import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultAddressServiceImpl implements DefaultAddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;


    @Override
    public void setDefaultAddressForUser(String username, UUID addressId) {

        User user = userService.findByUsername(username);
        System.out.println(user.getFirstName() + "1111111111111111111111111111111111111111111111");
        Address selectedAddress = addressRepository.findById(addressId).orElse(null);
        List<Address> addressList = addressRepository.findByIsDeletedFalse();
        System.out.println(selectedAddress.getId() + "                111111111111111111 this is selcted address id ");
        for (Address address : addressList) {
            address.setDefault(false);
            addressRepository.save(address);
        }
        selectedAddress.setDefault(true);
        addressRepository.save(selectedAddress);

    }

    @Override
    public Address getDefaultAddressForUser(String username) {
        return null;
    }

}
