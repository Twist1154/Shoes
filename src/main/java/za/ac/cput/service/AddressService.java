package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Address;
import za.ac.cput.repository.AddressRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * AddressService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 *
 */
@Slf4j
@Service
@Transactional
public class AddressService implements IAddress{

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    /**
     * @param address The entity to be created.
     * @return
     */
    @Override
    public Address create(Address address) {
        return repository.save(address);
    }

    /**
     * @param id The ID of the entity to be read.
     * @return address
     */
    @Override
    public Address read(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public Address update(Address address) {
        Address existingAddress = repository.findById(address.getId()).orElse(null);
        if (existingAddress != null) {
            Address updatedAddress = new Address.Builder()
                    .copy(existingAddress)
                    .setTitle(address.getTitle())
                    .setAddressLine1(address.getAddressLine1())
                    .setAddressLine2(address.getAddressLine2())
                    .setCountry(address.getCountry())
                    .setCity(address.getCity())
                    .setPostalCode(address.getPostalCode())
                    .setPhoneNumber(address.getPhoneNumber())
                    .setCreatedAt(address.getCreatedAt())
                    .setUpdatedAt(address.getUpdatedAt())
                    .build();
            return repository.save(updatedAddress);
        } else {
            return null;
        }
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }


    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent Wishlist with ID: " + id);
            return false;
        }
    }

    @Override
    public Optional<Address> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Address> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public List<Address> findByAddressLine1(String addressLine1) {
        return repository.findByAddressLine1(addressLine1);
    }

    @Override
    public List<Address> findByAddressLine2(String addressLine2) {
        return repository.findByAddressLine2(addressLine2);
    }

    @Override
    public List<Address> findByCountry(String country) {
        return repository.findByCountry(country);
    }

    @Override
    public List<Address> findByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public List<Address> findByPostalCode(String postalCode) {
        return repository.findByPostalCode(postalCode);
    }

    @Override
    public List<Address> findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Address> findByCreatedAtAfter(LocalDateTime createdAt) {
        return repository.findByCreatedAtAfter(createdAt);
    }

    @Override
    public List<Address> findByUpdatedAt(LocalDateTime updatedAt) {
        return repository.findByUpdatedAt(updatedAt);
    }
}
