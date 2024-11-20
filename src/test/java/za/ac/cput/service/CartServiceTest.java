package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;

    private Cart cart;
    private CartItem cartItem;
    private User user;

    @BeforeEach
    void setUp() {
        // Ensure the user exists or create a new one
        user = userService.read(2L); // Assuming user with ID 2 exists, otherwise create
        if (user == null) {
            String Username = "User" + System.currentTimeMillis();
            String email = "User" + System.currentTimeMillis()+ "@example.com";
            user = UserFactory.createUser(
                    null,
                    "avatar.jpg",
                    "John",
                    "Doe",
                    Username,
                    email,
                    LocalDate.parse("1990-01-01"),
                    Set.of(Role.USER, Role.ADMIN),
                    "0123456789",
                    "password123"
            );
            user = userService.create(user);
            System.out.println("User created with ID: " + user.getId());
        }


        // Create a sample Cart object using the factory method
        cart = CartFactory.createCart(
                null,
                user,
                100.0,
                LocalDateTime.now(),
                null);

        cartService.create(cart);
    }

    @AfterEach
    void tearDown() {
        if(cart.getId() != null && cart.getId() > 2) {
            cartService.delete(cart.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        Cart createdCart = cartService.create(cart);
        assertNotNull(createdCart);
        assertEquals(0.0, createdCart.getTotal());
        assertNotNull(createdCart.getUser().getId());
    }

    @Test
    @Order(2)
    void read() {
        Cart createdCart = cartService.create(cart);  // First, create the cart
        Cart readCart = cartService.read(createdCart.getId());  // Then, read it
        assertNotNull(readCart);
        assertEquals(createdCart.getId(), readCart.getId());
    }

    @Test
    @Order(3)
    void update() {
        Cart createdCart = cartService.create(cart);
        createdCart = new Cart.Builder()
                .copy(createdCart)
                .setTotal(200.0)  // Update the total amount
                .build();
        Cart updatedCart = cartService.update(createdCart);
        assertNotNull(updatedCart);
        assertEquals(200.0, updatedCart.getTotal());
    }

    @Test
    @Order(4)
    void delete() {
        Cart createdCart = cartService.create(cart);
        cartService.delete(createdCart.getId());
        Cart deletedCart = cartService.read(createdCart.getId());
        assertTrue(deletedCart == null);  // Ensure the cart is deleted
    }

    @Test
    @Order(5)
    void findAll() {
        List<Cart> carts = cartService.findAll();
        assertNotNull(carts);
        assertTrue(carts.size() > 0);  // Ensure there's at least one cart
    }
}
