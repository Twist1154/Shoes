package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.User;
import za.ac.cput.service.AddressService;
import za.ac.cput.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * AddressController.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 28-Aug-24
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {
        Address newAddress = addressService.create(address);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> read(@PathVariable Long id) {
        Address address = addressService.read(id);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/")
    public ResponseEntity<Address> update(@RequestBody Address address) {
        Address updatedAddress = addressService.update(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Address>> findAll() {
        List<Address> addresses = addressService.findAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user")
    public ResponseEntity<Optional<Address>> findByUser(@RequestBody Long userId) {
        Optional<Address> addresses = addressService.findByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Address>> findByTitle(@PathVariable String title) {
        List<Address> addresses = addressService.findByTitle(title);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/address-line1/{addressLine1}")
    public ResponseEntity<List<Address>> findByAddressLine1(@PathVariable String addressLine1) {
        List<Address> addresses = addressService.findByAddressLine1(addressLine1);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/address-line2/{addressLine2}")
    public ResponseEntity<List<Address>> findByAddressLine2(@PathVariable String addressLine2) {
        List<Address> addresses = addressService.findByAddressLine2(addressLine2);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Address>> findByCountry(@PathVariable String country) {
        List<Address> addresses = addressService.findByCountry(country);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Address>> findByCity(@PathVariable String city) {
        List<Address> addresses = addressService.findByCity(city);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/postal-code/{postalCode}")
    public ResponseEntity<List<Address>> findByPostalCode(@PathVariable String postalCode) {
        List<Address> addresses = addressService.findByPostalCode(postalCode);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<List<Address>> findByPhoneNumber(@PathVariable String phoneNumber) {
        List<Address> addresses = addressService.findByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/created-after/{createdAt}")
    public ResponseEntity<List<Address>> findByCreatedAtAfter(@PathVariable LocalDateTime createdAt) {
        List<Address> addresses = addressService.findByCreatedAtAfter(createdAt);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/updated-after/{date}")
    public ResponseEntity<List<Address>> findByUpdatedAt(@PathVariable String date) {
        LocalDateTime updatedAt = LocalDateTime.parse(date);
        List<Address> address = addressService.findByUpdatedAt(updatedAt);
        return ResponseEntity.ok(address);
    }
}
