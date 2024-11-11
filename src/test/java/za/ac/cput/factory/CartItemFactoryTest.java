package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;
import za.ac.cput.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link CartItemFactory}.
 */
class CartItemFactoryTest {

    private Cart cart;
    private Product product;
    private ProductSku productSku;
    private User user;
    private Set<Role> roles;

    @BeforeEach
    void setup() {
        // Initialize valid objects for testing
        roles = new HashSet<>(Set.of(Role.USER, Role.ADMIN));

        // Set up a sample User object using the factory method
        user = UserFactory.createUser(
                null,
                "avatar.jpg",
                "John",
                "Doe",
                "user1",
                "johndoe@example.com",
                LocalDate.parse("1990-01-01"),
                roles,
                "0123456789",
                "password123");
        // Create a sample Cart object using the factory method
         cart = CartFactory.createCart(
                1L,
                user,
                100.0,
                LocalDateTime.now(),
                null);
        product = new Product();  // Assuming Product has a no-arg constructor
        productSku = new ProductSku();  // Assuming ProductSkuService has a no-arg constructor
    }

    @Test
    void testCreateCartItem_Success() {
        // Test successful CartItem creation with valid input
        CartItem cartItem = CartItemFactory.createCartItem(
                1L,
                cart,
                product,
                productSku,
                10);

        assertNotNull(cartItem, "CartItem should not be null");
        assertEquals(10, cartItem.getQuantity(), "Quantity should be 10");

        // Print the created CartItem object to the terminal
        System.out.println("Created CartItem: " + cartItem);
    }

    @Test
    void testCreateCartItem_WithNullCart_ThrowsIllegalArgumentException() {
        // Test with null Cart, expecting IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CartItemFactory.createCartItem(
                        1L,
                        null,
                        product,
                        productSku,
                        10
                )
        );

        assertEquals("Cart cannot be null", exception.getMessage());

        // Print the exception message to the terminal
        System.out.println("Expected exception: " + exception.getMessage());
    }

    @Test
    void testCreateCartItem_WithNullProduct_ThrowsIllegalArgumentException() {
        // Test with null Product, expecting IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CartItemFactory.createCartItem(
                        1L,
                        cart,
                        null,
                        productSku,
                        10
                )
        );

        assertEquals("Product cannot be null", exception.getMessage());

        // Print the exception message to the terminal
        System.out.println("Expected exception: " + exception.getMessage());
    }

    @Test
    void testCreateCartItem_WithNullProductSku_ThrowsIllegalArgumentException() {
        // Test with null ProductSkuService, expecting IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CartItemFactory.createCartItem(
                        1L,
                        cart,
                        product,
                        null,
                        10
                )
        );

        assertEquals("ProductSkuService cannot be null", exception.getMessage());

        // Print the exception message to the terminal
        System.out.println("Expected exception: " + exception.getMessage());
    }

    @Test
    void testCreateCartItem_WithInvalidQuantity_ThrowsIllegalArgumentException() {
        // Test with zero quantity, expecting IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CartItemFactory.createCartItem(
                        1L,
                        cart,
                        product,
                        productSku,
                        0));

        assertEquals("Quantity must be greater than zero", exception.getMessage());

        // Print the exception message to the terminal
        System.out.println("Expected exception: " + exception.getMessage());
    }

    @Test
    void testCreateCartItem_WithMultipleNullValues_ThrowsIllegalArgumentException() {
        // Test with null Cart and Product, expecting IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CartItemFactory.createCartItem(
                        1L,
                        null,
                        null,
                        productSku,
                        10
                )
        );

        assertEquals("Cart and Product cannot be null", exception.getMessage());

        // Print the exception message to the terminal
        System.out.println("Expected exception: " + exception.getMessage());
    }

    @Test
    void testCreateCartItem_AllNullInputs_ThrowsIllegalArgumentException() {
        // Test with all null values and invalid quantity, expecting IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CartItemFactory.createCartItem(
                        1L,
                        null,
                        null,
                        null,
                        0
                )
        );

        assertEquals("Cart, Product, ProductSkuService cannot be null and Quantity must be greater than zero", exception.getMessage());

        // Print the exception message to the terminal
        System.out.println("Expected exception: " + exception.getMessage());
    }
}
