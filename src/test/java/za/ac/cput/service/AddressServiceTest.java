package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.Application;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;
import za.ac.cput.repository.AddressRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_CLASS)
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    private Address address;
    private User user;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        user = userService.read(2L);

        // Initialize the address object and assign it to the class-level variable
        address = new Address.Builder()
                .setId(null)
                .setUser(user)
                .setTitle("Home")
                .setAddressLine1("Apt 101")
                .setAddressLine2("New York")
                .setCountry("South Africa")
                .setCity("Cape Town")
                .setPostalCode("9320")
                .setPhoneNumber("1234567890")
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(null)
                .build();
    }

    @AfterEach
    void tearDown() {
        // Ensure that the address is deleted after each test
        if (address.getId() != null) {
            addressRepository.deleteById(address.getId());
        }
    }

    @Test
    @Order(1)
    void testCreateAddress() {
        Address createdAddress = addressService.create(address);
        assertNotNull(createdAddress);
        assertEquals(address.getId(), createdAddress.getId()); // Check that the created address has the correct ID
    }

    @Test
    @Order(2)
    void testReadAddress() {
        Address createdAddress = addressService.create(address);
        Address readAddress = addressService.read(createdAddress.getId());
        assertNotNull(readAddress);
        assertEquals(createdAddress.getUser().getId(), readAddress.getUser().getId()); // Verify user ID
    }

    @Test
    @Order(3)
    void testUpdateAddress() {
        Address createdAddress = addressService.create(address);
        System.out.println("Created Address for Update Test: " + createdAddress);

        // Update the address object
        createdAddress = new Address.Builder()
                .copy(createdAddress)
                .setAddressLine1("456 Elm St")
                .setCountry("Nigeria")
                .build();

        Address updatedAddress = addressService.update(createdAddress);
        System.out.println("Updated Address: " + updatedAddress);

        assertNotNull(updatedAddress);
        assertEquals(createdAddress.getId(), updatedAddress.getId());
        assertEquals("456 Elm St", updatedAddress.getAddressLine1()); // Ensure the address was updated
    }

    @Test
    @Order(4)
    void testFindAllAddresses() {
        addressService.create(address); // Ensure address is created
        List<Address> addresses = addressService.findAll();
        assertNotNull(addresses);
    }

    @Test
    @Order(5)
    void testFindByUser() {
        Optional<Address> foundAddress = addressService.findByUserId(user.getId());
        assertNotNull(foundAddress);
    }

    @Test
    @Order(6)
    void testFindByTitle() {
        addressService.create(address); // Ensure address is created
        List<Address> addresses = addressService.findByTitle("Home");
        assertNotNull(addresses);
    }
}
