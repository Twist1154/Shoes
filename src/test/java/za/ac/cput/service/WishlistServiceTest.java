package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class WishlistServiceTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    private Wishlist wishlist;
    private Product product;
    private User user;
    private WishlistItem item1, item2;

    @BeforeEach
    void setup() {
        product = productService.read(1L);

        user = userService.read(2L);
        if (user == null) {
            // Generate a unique SKU
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

        // Initialize Wishlist
        wishlist = new Wishlist.Builder()
                .setUser(user)
                .setCreatedAt(LocalDateTime.now())
                .build();
        wishlist = wishlistRepository.save(wishlist);

        // Initialize WishlistItems
        item1 = new WishlistItem.Builder()
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist)
                .build();

        item2 = new WishlistItem.Builder()
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist)
                .build();

        // Add items to wishlist
        wishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setWishlistItems(List.of(item1, item2))
                .build();

        wishlistRepository.save(wishlist); // Persist wishlist with items
    }

    @AfterEach
    void tearDown() {
        if (wishlistRepository.existsById(wishlist.getId()) && wishlist.getId() != 1)
        wishlistRepository.delete(wishlist);
    }

    @Test
    @Order(1)
    void testCreateWishlist() {
        Wishlist newWishlist = new Wishlist.Builder()
                .setUser(user)
                .setCreatedAt(LocalDateTime.now())
                .build();
        Wishlist createdWishlist = wishlistService.create(newWishlist);

        assertNotNull(createdWishlist);
        assertEquals(newWishlist.getUser().getId(), createdWishlist.getUser().getId());
        assertIterableEquals(newWishlist.getWishlistItems(), createdWishlist.getWishlistItems());
        System.out.println("Created Wishlist: " + createdWishlist);
    }

    @Test
    @Order(2)
    @Transactional
    void testReadWishlist() {
        Wishlist readWishlist = wishlistService.read(wishlist.getId());

        assertNotNull(readWishlist);
        assertEquals(wishlist.getUser().getId(), readWishlist.getUser().getId());
        System.out.println("Read Wishlist: " + readWishlist);
    }

    @Test
    @Order(3)
    @Transactional
    void testUpdateWishlist() {
        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setUser(user)
                .build();
        updatedWishlist = wishlistService.update(updatedWishlist);

        assertNotNull(updatedWishlist);
        assertEquals(wishlist.getUser().getId(), updatedWishlist.getUser().getId());
        System.out.println("Updated Wishlist: " + updatedWishlist);
    }

    @Test
    @Order(4)
    void testDeleteWishlist() {
        wishlistService.create(wishlist);
        boolean deleted = wishlistService.delete(wishlist.getId());
        assertTrue(deleted);
    }

    @Test
    @Order(5)
    void testFindAllWishlists() {
        List<Wishlist> allWishlists = wishlistService.findAll();

        assertNotNull(allWishlists);
        assertFalse(allWishlists.isEmpty());
        System.out.println("All Wish Lists: " + allWishlists);
    }
}
