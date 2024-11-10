package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import za.ac.cput.domain.*;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.ImageUrlsFactory;
import za.ac.cput.factory.ProductFactory;
import za.ac.cput.factory.SubCategoryFactory;
import za.ac.cput.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static za.ac.cput.enums.Role.USER;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class WishlistServiceTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private ProductService productService;

    private Wishlist wishlist;
    private Product product;
    private List<SubCategory> subCategories;
    private Category category;
    private ImageUrls imageUrls;
    private User user;
    private List<WishlistItem> wishListItems;

    @BeforeEach
    void setup() {
        product = productService.read(1L);
        // Set up Category and save to get generated ID
        category = CategoryFactory.createCategory(
                null, // ID will be generated
                "Sneakers"
        );
        category = categoryRepository.save(category);

        // Create and save SubCategory objects
        SubCategory subCategory1 = SubCategoryFactory.createSubCategory(
                null, // ID will be generated
                category,
                product
        );
        SubCategory subCategory2 = SubCategoryFactory.createSubCategory(
                null, // ID will be generated
                category,
                product
        );

        subCategories = List.of(subCategory1, subCategory2);
        subCategoryRepository.saveAll(subCategories); // Save all at once

        // Create and save ImageUrls object
        imageUrls = ImageUrlsFactory.createImageUrls(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg"
        );

        // Create and save Product object
        product = ProductFactory.createProduct(
                null, // ID will be generated
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                imageUrls,
                subCategories,
                LocalDateTime.now()
        );
        product = productRepository.save(product);

        // Set up User and save to get generated ID
        user = new User.Builder()
                .setFirstName("Rethabile")
                .setLastName("Ntsekhe")
                .setEmail("rethabile1154@gmail.com") // Ensure email matches used in tests
                .setPassword("password") // Use encoded password
                .setRole(Set.of(Role.USER, Role.ADMIN))
                .setBirthDate(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("1234567890")
                .build();
        user = userRepository.save(user);

        // Set up Wishlist and WishlistItems
        wishlist = new Wishlist.Builder()
                .setUser(user)
                .setCreatedAt(LocalDateTime.now())
                .build();
        wishlist = wishlistRepository.save(wishlist); // Save Wishlist first to get ID

        WishlistItem item1 = new WishlistItem.Builder()
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist) // Reference the saved Wishlist
                .build();
        WishlistItem item2 = new WishlistItem.Builder()
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist) // Reference the saved Wishlist
                .build();

        wishListItems = List.of(item1, item2);


        wishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setWishlistItems(wishListItems)
                .build();


        wishlistRepository.save(wishlist); // Save again to persist WishlistItems
    }

    @AfterEach
    void tearDown() {
       /* wishlistRepository.deleteAll();
        userRepository.deleteAll(); // Clean up User repository
        productRepository.deleteAll(); // Clean up Product repository
        subCategoryRepository.deleteAll(); // Clean up SubCategory repository
        categoryRepository.deleteAll(); // Clean up Category repository*/
    }

    @Test
    @Order(1)
    void testCreateWishlist() {
        // Create a new Wishlist using the builder
        Wishlist newWishlist = new Wishlist.Builder()
                .setUser(user)
                .setWishlistItems(wishListItems) // Use existing WishlistItems
                .setCreatedAt(LocalDateTime.now())
                .build();
        Wishlist createdWishlist = wishlistService.create(newWishlist);

        // Assert that the created Wishlist is not null
        assertNotNull(createdWishlist);
        System.out.println("Created Wishlist: " + createdWishlist);
        assertEquals(newWishlist.getUser().getId(), createdWishlist.getUser().getId());
        assertEquals(newWishlist.getWishlistItems().size(), createdWishlist.getWishlistItems().size());
    }

    @Test
    @Order(2)
    void testReadWishlist() {
        Wishlist readWishlist = wishlistService.read(wishlist.getId());

        System.out.println("Read Wishlist: " + readWishlist);
        assertNotNull(readWishlist);
        assertEquals(wishlist.getUser().getId(), readWishlist.getUser().getId());
    }

    @Test
    @Order(3)
    void testUpdateWishlist() {
        wishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setUser(user)
                .build()
        ; // Update wishlist to set deletedAt
        Wishlist updatedWishlist = wishlistService.update(wishlist);

        // Print the updated wishlist to the terminal
        System.out.println("Updated: " + updatedWishlist);
        System.out.println("--------------------------------------------------------------------");

        assertNotNull(updatedWishlist);
        assertEquals(wishlist.getUser().getId(), updatedWishlist.getUser().getId());
    }

    @Test
    @Order(4)
    void testDeleteWishlist() {
        // Delete the Wishlist using the service
        wishlistService.delete(wishlist.getId());
        Optional<Wishlist> deletedWishlist = wishlistRepository.findById(wishlist.getId());
        assertTrue(deletedWishlist.isEmpty());
    }

    @Test
    @Order(5)
    void testFindAllWishlists() {
        // Find all Wishlists using the service
        List<Wishlist> allWishlists = wishlistService.findAll();

        // Print all wishlists to the terminal
        System.out.println("All Wish Lists: " + allWishlists);
        System.out.println("--------------------------------------------------------------------");

        // Assert that the list of Wishlists is not null
        assertNotNull(allWishlists);
        assertFalse(allWishlists.isEmpty());
    }
}
