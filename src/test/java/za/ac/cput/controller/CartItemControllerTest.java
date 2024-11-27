package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.*;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.service.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/api/cart-items";

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    private Cart cart;
    private Product product;
    private ProductSku productSku;
    private User user;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        user = userService.read(2L);
        product = productService.read(1L);
        productSku = productSkuService.read(1L);

        cart = CartFactory.createCart(
                1L,
                user,
                800.0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        cart = cartService.create(cart);

        // Create a CartItem
        cartItem = CartItemFactory.createCartItem(
                null,
                cart,
                product,
                productSku,
                2
        );
        ResponseEntity<CartItem> response = restTemplate.postForEntity(baseUrl, cartItem, CartItem.class);
        cartItem = response.getBody();
        assertNotNull(cartItem);
        assertNotNull(cartItem.getId());
    }

    @AfterEach
    void tearDown() {
        if (cartItem.getId() != null && cartItem.getId() >= 3) {
            cartItemService.delete(cartItem.getId());
        }
        if (cart.getId() != null && cart.getId() >= 3) {
            cartService.delete(cart.getId());
        }
    }

    @Test
    @Order(1)
    void createCartItem() {
        CartItem newCartItem = CartItemFactory.createCartItem(
                null,
                cart,
                product,
                productSku,
                3
        );
        ResponseEntity<CartItem> response = restTemplate.postForEntity(baseUrl, newCartItem, CartItem.class);
        CartItem createdCartItem = response.getBody();
        assertNotNull(createdCartItem);
        assertNotNull(createdCartItem.getId());
        assertEquals(3, createdCartItem.getQuantity());
    }

    @Test
    @Order(2)
    void getCartItemById() {
        CartItem fetchedCartItem = restTemplate.getForObject(baseUrl + "/" + cartItem.getId(), CartItem.class);
        assertNotNull(fetchedCartItem);
        assertEquals(cartItem.getId(), fetchedCartItem.getId());
    }

    @Test
    @Order(3)
    void updateCartItem() {
        cartItem = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(5)
                .build();

        restTemplate.put(baseUrl, cartItem);

        CartItem updatedCartItem = restTemplate.getForObject(baseUrl + "/" + cartItem.getId(), CartItem.class);
        assertNotNull(updatedCartItem);
        assertEquals(5, updatedCartItem.getQuantity());
    }

    @Test
    @Order(4)
    void deleteCartItem() {
        CartItem cartItemToDelete = CartItemFactory.createCartItem(
                null,
                cart,
                product,
                productSku,
                1
        );
        ResponseEntity<CartItem> response = restTemplate.postForEntity(baseUrl, cartItemToDelete, CartItem.class);
        Long cartItemId = response.getBody().getId();
        assertNotNull(cartItemId);

        restTemplate.delete(baseUrl + "/" + cartItemId);

        ResponseEntity<CartItem> deletedCartItemResponse = restTemplate.getForEntity(baseUrl + "/" + cartItemId, CartItem.class);
        assertEquals(404, deletedCartItemResponse.getStatusCode());  // Assuming 404 for not found
    }

    @Test
    @Order(5)
    void getAllCartItems() {
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(baseUrl, CartItem[].class);
        List<CartItem> cartItems = Arrays.asList(response.getBody());

        assertNotNull(cartItems);
        assertFalse(cartItems.isEmpty());
    }

    @Test
    @Order(6)
    void getCartItemsByCartId() {
        String url = baseUrl + "/cart/" + cart.getId();
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(url, CartItem[].class);
        List<CartItem> cartItems = Arrays.asList(response.getBody());

        assertNotNull(cartItems);
        assertFalse(cartItems.isEmpty());
        assertTrue(cartItems.stream().allMatch(ci -> ci.getCart().getId().equals(cart.getId())));
    }

    @Test
    @Order(7)
    void getCartItemsByProductId() {
        String url = baseUrl + "/product/" + product.getId();
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(url, CartItem[].class);
        List<CartItem> cartItems = Arrays.asList(response.getBody());

        assertNotNull(cartItems);
        assertFalse(cartItems.isEmpty());
        assertTrue(cartItems.stream().allMatch(ci -> ci.getProduct().getId().equals(product.getId())));
    }

    @Test
    @Order(8)
    void getCartItemsByProductSkuId() {
        String url = baseUrl + "/product-sku/" + productSku.getId();
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(url, CartItem[].class);
        List<CartItem> cartItems = Arrays.asList(response.getBody());

        assertNotNull(cartItems);
        assertFalse(cartItems.isEmpty());
        assertTrue(cartItems.stream().allMatch(ci -> ci.getProductSku().getId().equals(productSku.getId())));
    }

    @Test
    @Order(9)
    void getCartItemsByQuantity() {
        int quantity = 2;
        String url = baseUrl + "/quantity/" + quantity;
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(url, CartItem[].class);
        List<CartItem> cartItems = Arrays.asList(response.getBody());

        assertNotNull(cartItems);
        assertFalse(cartItems.isEmpty());
        assertTrue(cartItems.stream().allMatch(ci -> ci.getQuantity() == quantity));
    }
}
