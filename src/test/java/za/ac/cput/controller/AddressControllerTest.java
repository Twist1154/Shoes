package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;
import za.ac.cput.service.AddressService;
import za.ac.cput.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/api/addresses";

    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    private Address address;
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.read(2L);

        address = new Address.Builder()
                .setUser(user)
                .setTitle("Home")
                .setAddressLine1("Apt 101")
                .setAddressLine2("New York")
                .setCountry("South Africa")
                .setCity("Cape Town")
                .setPostalCode("9320")
                .setPhoneNumber("1234567890")
                .setCreatedAt(LocalDateTime.now())
                .build();

        ResponseEntity<Address> response = restTemplate.postForEntity(baseUrl, address, Address.class);
        address = response.getBody();
        assertNotNull(address);
        assertNotNull(address.getId());
    }

    @AfterEach
    void tearDown() {
        if (address != null && address.getId() != null) {
            addressService.delete(address.getId());
        }
    }

    @Test
    void create() {
        Address newAddress = new Address.Builder()
                .setUser(user)
                .setTitle("Work")
                .setAddressLine1("Apt 202")
                .setAddressLine2("New York")
                .setCountry("South Africa")
                .setCity("Cape Town")
                .setPostalCode("9320")
                .setPhoneNumber("0987654321")
                .setCreatedAt(LocalDateTime.now())
                .build();

        ResponseEntity<Address> response = restTemplate.postForEntity(baseUrl, newAddress, Address.class);
        Address createdAddress = response.getBody();
        assertNotNull(createdAddress);
        assertNotNull(createdAddress.getId());
    }

    @Test
    void read() {
        ResponseEntity<Address> response = restTemplate.getForEntity(baseUrl + "/" + address.getId(), Address.class);
        assertEquals(address.getId(), response.getBody().getId());
    }

    @Test
    void update() {
        address = new Address.Builder()
                .copy(address)
                .setTitle("Updated Title")
                .build();

        restTemplate.put(baseUrl + "/" + address.getId(), address);
        ResponseEntity<Address> response = restTemplate.getForEntity(baseUrl + "/" + address.getId(), Address.class);
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    @Test
    void findAll() {
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
    }

    @Test
    void delete() {
        restTemplate.delete(baseUrl + "/" + address.getId());
        ResponseEntity<Address> response = restTemplate.getForEntity(baseUrl + "/" + address.getId(), Address.class);
        assertEquals(404, response.getStatusCode()); // Assuming 404 for not found
    }

    @Test
    void findByUser() {
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/user/" + user.getId(), Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getUser().getId().equals(user.getId())));
    }

    @Test
    void findByTitle() {
        String title = "Home";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/title/" + title, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getTitle().equals(title)));
    }

    @Test
    void findByAddressLine1() {
        String addressLine1 = "Apt 101";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/address-line1/" + addressLine1, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getAddressLine1().equals(addressLine1)));
    }

    @Test
    void findByAddressLine2() {
        String addressLine2 = "New York";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/address-line2/" + addressLine2, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getAddressLine2().equals(addressLine2)));
    }

    @Test
    void findByCountry() {
        String country = "South Africa";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/country/" + country, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getCountry().equals(country)));
    }

    @Test
    void findByCity() {
        String city = "Cape Town";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/city/" + city, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getCity().equals(city)));
    }

    @Test
    void findByPostalCode() {
        String postalCode = "9320";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/postal-code/" + postalCode, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getPostalCode().equals(postalCode)));
    }

    @Test
    void findByPhoneNumber() {
        String phoneNumber = "1234567890";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/phone-number/" + phoneNumber, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getPhoneNumber().equals(phoneNumber)));
    }

    @Test
    void findByCreatedAtAfter() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/created-after/" + createdAt, Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getCreatedAt().isAfter(createdAt)));
    }

    @Test
    void findByUpdatedAt() {
        address = new Address.Builder().copy(address).setUpdatedAt(LocalDateTime.now()).build();
        addressService.update(address);

        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/updated-at/" + address.getUpdatedAt(), Address[].class);
        List<Address> addresses = Arrays.asList(response.getBody());
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.stream().allMatch(addr -> addr.getUpdatedAt().equals(address.getUpdatedAt())));
    }
}
