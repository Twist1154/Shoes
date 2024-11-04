package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.WishlistItem;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.repository.WishlistItemRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistItemServiceTest {

    @Autowired
    private WishlistItemService service;

    @Autowired
    private WishlistItemRepository repository;

    private Product product;
    private Wishlist wishlist;
    private List<WishlistItem> wishlistItems;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        product = productService.read(16L);  // Fetching a product from the database
        wishlist = wishlistService.read(7L); // Fetching a wishlist from the database

        // Create WishListItems
        WishlistItem item1 = new WishlistItem.Builder()
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist)
                .build();

        WishlistItem item2 = new WishlistItem.Builder()
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist)
                .build();

        wishlistItems = List.of(item1, item2);

        wishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setWishlistItems(wishlistItems)
                .build();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll(); // Optional, to clean up after each test
    }

    @Test
    @Order(1)
    void create() {
        // Test creating a new wishlist item
        WishlistItem createdItem = service.create(wishlistItems.get(0));
        assertNotNull(createdItem);
        assertEquals(wishlistItems.get(0).getProduct().getId(), createdItem.getProduct().getId());
        assertEquals(wishlistItems.get(0).getWishlist().getId(), createdItem.getWishlist().getId());
        assertEquals(wishlistItems.get(0).getDateAdded(), createdItem.getDateAdded());
    }

    @Test
    @Order(2)
    void read() {
        // Test reading a wishlist item by ID
        WishlistItem createdItem = service.create(wishlistItems.get(0));
        WishlistItem readItem = service.read(createdItem.getId());
        assertNotNull(readItem);
        assertEquals(createdItem.getId(), readItem.getId());
    }

    @Test
    @Order(3)
    void update() {
        // Test updating a wishlist item
        WishlistItem createdItem = service.create(wishlistItems.get(0));
        WishlistItem updatedItem = new WishlistItem.Builder()
                .copy(createdItem)
                .setDateAdded(LocalDateTime.now().plusDays(1)) // Update the date
                .build();

        WishlistItem result = service.update(updatedItem);
        assertNotNull(result);
        assertEquals(updatedItem.getDateAdded(), result.getDateAdded());
    }

    @Test
    @Order(4)
    void findAll() {
        // Test finding all wishlist items
        service.create(wishlistItems.get(0));
        service.create(wishlistItems.get(1));
        List<WishlistItem> items = service.findAll();
        assertFalse(items.isEmpty());
    }

    @Test
    @Order(5)
    void delete() {
        // Test deleting a wishlist item by ID
        WishlistItem createdItem = service.create(wishlistItems.get(0));
        service.delete(createdItem.getId());
        WishlistItem deletedItem = service.read(createdItem.getId());
        assertNull(deletedItem); // The item should be deleted, so reading should return null

    }
}
