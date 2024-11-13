package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.service.CartItemService;
import za.ac.cput.service.CartService;
import za.ac.cput.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/carts";

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    private Cart cart;
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.read(2L);
        cart = CartFactory.createCart(
                null,
                user,
                100.0,
                LocalDateTime.now(),
                null
        );
        ResponseEntity<Cart> response = restTemplate.postForEntity(baseUrl, cart, Cart.class);
        cart = response.getBody();
        assertNotNull(cart);
        assertNotNull(cart.getId());
    }

    @AfterEach
    void tearDown() {
        if (cart.getId() != null && cart.getId() != 1) {
            restTemplate.delete(baseUrl + "/" + cart.getId());
        }
    }

    @Test
    @Order(1)
    void createCart() {
        Cart newCart = CartFactory.createCart(
                null,
                user,
                200.0,
                LocalDateTime.now(),
                null
        );
        assertNotNull(newCart);
        ResponseEntity<Cart> response = restTemplate.postForEntity(baseUrl, newCart, Cart.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    @Order(2)
    void getCartById() {
        Cart fetchedCart = restTemplate.getForObject(baseUrl + "/" + cart.getId(), Cart.class);
        assertNotNull(fetchedCart);
        assertEquals(cart.getId(), fetchedCart.getId());
    }

    @Test
    @Order(3)
    void updateCart() {
        cart = new Cart.Builder()
                .copy(cart)
                .setTotal(150.0)
                .build();

        restTemplate.put(baseUrl, cart);

        Cart updatedCart = restTemplate.getForObject(baseUrl + "/" + cart.getId(), Cart.class);

        assertNotNull(updatedCart);
        assertEquals(cart.getId(), updatedCart.getId());
        assertEquals(150.0, updatedCart.getTotal());
    }

    @Test
    @Order(4)
    void deleteCart() {
        Cart cartToDelete = CartFactory.createCart(
                null,
                user,
                50.0,
                LocalDateTime.now(),
                null
        );
        ResponseEntity<Cart> response = restTemplate.postForEntity(baseUrl, cartToDelete, Cart.class);
        Long cartId = response.getBody().getId();
        assertNotNull(cartId);

        restTemplate.delete(baseUrl + "/" + cartId);

        ResponseEntity<Cart> deletedCartResponse = restTemplate.getForEntity(baseUrl + "/" + cartId, Cart.class);
        assertEquals(404, deletedCartResponse.getStatusCodeValue());  // Assuming 404 is returned for not found
    }

    @Test
    @Order(5)
    void getAllCarts() {
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(baseUrl, Cart[].class);
        List<Cart> carts = Arrays.asList(response.getBody());

        assertNotNull(carts);
        assertFalse(carts.isEmpty());
    }

    @Test
    @Order(6)
    void getCartsByUserId() {
        String url = baseUrl + "/user/" + user.getId();
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(url, Cart[].class);
        List<Cart> carts = Arrays.asList(response.getBody());

        assertNotNull(carts);
        assertFalse(carts.isEmpty());
        assertTrue(carts.stream().allMatch(c -> c.getUser().getId().equals(user.getId())));
    }

    @Test
    @Order(7)
    void getCartsCreatedAfter() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        String url = baseUrl + "/created-after/" + date;
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(url, Cart[].class);
        List<Cart> carts = Arrays.asList(response.getBody());

        assertNotNull(carts);
        assertFalse(carts.isEmpty());
        assertTrue(carts.stream().allMatch(c -> c.getCreatedAt().isAfter(date)));
    }

    @Test
    @Order(8)
    void getCartsByTotalGreaterThan() {
        double minTotal = 50.0;
        String url = baseUrl + "/total-greater-than/" + minTotal;
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(url, Cart[].class);
        List<Cart> carts = Arrays.asList(response.getBody());

        assertNotNull(carts);
        assertFalse(carts.isEmpty());
        assertTrue(carts.stream().allMatch(c -> c.getTotal() > minTotal));
    }

    @Test
    @Order(9)
    void getCartsUpdatedAfter() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        String url = baseUrl + "/updated-after/" + date;
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(url, Cart[].class);
        List<Cart> carts = Arrays.asList(response.getBody());

        assertNotNull(carts);
        assertFalse(carts.isEmpty());
        assertTrue(carts.stream().allMatch(c -> c.getUpdatedAt().isAfter(date)));
    }
}
