package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
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
        user = userService.read(2L);


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
