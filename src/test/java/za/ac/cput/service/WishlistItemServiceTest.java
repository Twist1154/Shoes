package za.ac.cput.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.factory.WishlistFactory;
import za.ac.cput.repository.WishlistItemRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_CLASS)
@Transactional
class WishlistItemServiceTest {

    @Autowired
    private WishlistItemService service;

    @Autowired
    private WishlistItemRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    private Product product;
    private Wishlist wishlist;
    private WishlistItem item1, item2;
    private User user;

    @BeforeEach
    void setUp() {
        product = productService.read(1L);

        // Create User if not already created
        user = userService.read(2L);
        if (user == null) {
            user = UserFactory.createUser(
                    null,
                    "avatar.jpg",
                    "John",
                    "Doe",
                    "User" + System.currentTimeMillis(),
                    "user" + System.currentTimeMillis() + "@example.com",
                    LocalDate.parse("1990-01-01"),
                    Set.of(Role.USER, Role.ADMIN),
                    "0123456789",
                    "password123"
            );
            user = userService.create(user);
            System.out.println("User created with ID: " + user.getId());
        }

        // Create a new Wishlist and WishlistItems
        wishlist = WishlistFactory.createWishlist(
                null,
                user,
                LocalDateTime.now()
        );
        wishlist = wishlistService.create(wishlist);

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
    }

    @AfterEach
    void tearDown() {
        // Clean up the created WishlistItems
        if (item1.getId() != null && item1.getId() != 1) repository.deleteById(item1.getId());
        if (item2.getId() != null && item2.getId() != 2) repository.deleteById(item2.getId());
        if (wishlist.getId() != null && wishlist.getId() != 1) wishlistService.delete(wishlist.getId());
    }

    @Test
    @Order(1)
    void create() {
        WishlistItem createdItem = service.create(item1);
        assertNotNull(createdItem);
        assertEquals(item1.getProduct().getId(), createdItem.getProduct().getId());
        assertEquals(item1.getWishlist().getId(), createdItem.getWishlist().getId());
        assertEquals(item1.getDateAdded(), createdItem.getDateAdded());
    }

    @Test
    @Order(2)
    void read() {
        WishlistItem createdItem = service.create(item1);
        WishlistItem readItem = service.read(createdItem.getId());
        assertNotNull(readItem);
        assertEquals(createdItem.getId(), readItem.getId());
    }

    @Test
    @Order(3)
    void update() {
        WishlistItem createdItem = service.create(item1);

        // Update the dateAdded
        WishlistItem updatedItem = new WishlistItem.Builder()
                .copy(createdItem)
                .setDateAdded(LocalDateTime.now().plusDays(1))
                .build();

        WishlistItem result = service.update(updatedItem);
        assertNotNull(result);
        assertEquals(updatedItem.getDateAdded(), result.getDateAdded());
    }

    @Test
    @Order(4)
    void findAll() {
        service.create(item1);
        service.create(item2);
        List<WishlistItem> items = service.findAll();
        assertFalse(items.isEmpty());
    }

    @Test
    @Order(5)
    void delete() {
        WishlistItem item = service.create(item1);
        System.out.println("Created Item: " + item1);
        boolean isdeleted = service.delete(item.getId());
        WishlistItem deletedItem = service.read(item.getId());
        assertTrue(isdeleted);
        assertNull(service.read(item.getId()));

    }
}
