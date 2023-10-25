package com.ecomapp.ecomapp.service.address;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class AddressServiceImpl implements  AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }


    @Override
    public Optional<Address> findById(UUID id) {

        return addressRepository.findById(id);
    }

    @Override
    public List<Address> findByUser(User user) {
        return addressRepository.findByUser(user);
    }


    @Override
    public void deleteUserAddress(UUID id) {

    }

    @Override
    public void disableAddress(UUID addressId) {
        Optional<Address> userAddress = addressRepository.findById(addressId);
        if (userAddress.isPresent()) {
            Address address = userAddress.get();
            address.setDeleted(true);
            addressRepository.save(address);
        }
    }

    @Override
    public List<Address> getNonDeleteAddressByCustomer(User user) {
        return addressRepository.findByIsDeletedFalse();
    }

    @Override
    public Address getAddressById(UUID id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElse(null);
    }
//
//    @Override
//    public Address editAddress(UUID id, Address updatedAddress) {
//        return null;
//    }
   @Override
public Address findAddressById(UUID addressId) {

        return addressRepository.findById(addressId).orElse(null);
}

    @Override
    public Address updateAddress(UUID addressId, Address updatedAddress) {
        // Retrieve the existing address by ID
        Optional<Address> optionalAddress = addressRepository.findById(addressId);

        if (optionalAddress.isPresent()) {
            Address existingAddress = optionalAddress.get();
            // Update the existing address with new details
            existingAddress.setFlat(updatedAddress.getFlat());
            existingAddress.setArea(updatedAddress.getArea());
            existingAddress.setTown(updatedAddress.getTown());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPin(updatedAddress.getPin());
            existingAddress.setLandmark(updatedAddress.getLandmark());
            existingAddress.setDefaultAddress(updatedAddress.isDefaultAddress());

            // Save the updated address
            return addressRepository.save(existingAddress);
        }

        // Address not found
        return null;
    }
}

//    @Override
//    public Address getDefaultAddressForUser(User user) {
//        // Call the repository method to find the default address
//        return addressRepository.findFirstByUserAndDefaultAddressTrue(user);
//    }
//
//    @Override
//    public Address getDefaultAddress(String email) {
//        // Implement a method to retrieve the user's default address
//        // You may create a custom query in your repository interface
//        return addressRepository.findDefaultAddress(email);
//    }
//
//    public void setDefaultAddress(String email, UUID addressId) {
//        // Find the address by ID and set it as the default for the user
//        Address address = addressRepository.findById(addressId).orElse(null);
//
//        if (address != null) {
//            address.setDefaultAddress(true);
//            addressRepository.save(address);
//
//            // Optionally, you can update other addresses to mark them as not default
//            // This logic may depend on your business rules
//            addressRepository.updateNonDefaultAddresses(email, addressId);
//        }
//
//
//    }
//
//    @Override
//    public void clearDefaultAddresses(User user, UUID id) {
//        // Clear the default flag for all addresses associated with the user except for the one with the specified id.
//        addressRepository.clearDefaultAddresses(user, id);
//    }
//}
