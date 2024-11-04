package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.User;
import za.ac.cput.domain.WishlistItem;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.WishlistFactory;
import za.ac.cput.service.UserService;
import za.ac.cput.service.WishlistItemService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Wishlist wishlist;
    private List<WishlistItem> wishListItem;
    private User user;

    private final String baseUrl = "/wishlist"; // Base URL for the controller

    @Autowired
    private WishlistItemService wishListItemService;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = userService.read(2L); // Fetch a valid user

        WishlistItem item1 = wishListItemService.read(15L); // Valid WishListItems
        WishlistItem item2 = wishListItemService.read(16L);

        wishListItem = List.of(item1, item2);

        wishlist = WishlistFactory.createWishlist(
                null,
                user,
                wishListItem,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    @Order(1)
    void createWishlist() {
        ResponseEntity<Wishlist> response = restTemplate.postForEntity(baseUrl, wishlist, Wishlist.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        wishlist = response.getBody(); // Save for further tests
        System.out.println("Created Wishlist: " + wishlist);
    }

    @Test
    @Order(2)
    void getWishlistById() {
        String url = baseUrl + "/" + wishlist.getId();
        ResponseEntity<Wishlist> response = restTemplate.getForEntity(url, Wishlist.class);
        assertNotNull(response.getBody());
        assertEquals(wishlist.getId(), response.getBody().getId());
        System.out.println("Fetched Wishlist: " + response.getBody());
    }

    @Test
    @Order(3)
    void updateWishlist() {
        // Create an updated wishlist
        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setWishlistItems(List.of(wishListItemService.read(17L))) // Updating the items
                .build();

        restTemplate.put(baseUrl + "/" + wishlist.getId(), updatedWishlist, Wishlist.class);

        ResponseEntity<Wishlist> response = restTemplate.getForEntity(baseUrl + "/" + wishlist.getId(), Wishlist.class);
        assertNotNull(response.getBody());
        assertNotEquals(wishlist.getWishlistItems().size(), response.getBody().getWishlistItems().size());
        System.out.println("Updated Wishlist: " + response.getBody());
    }

    @Test
    @Order(4)
    void deleteWishlist() {
        String url = baseUrl + "/" + wishlist.getId();
        restTemplate.delete(url);

        ResponseEntity<Wishlist> response = restTemplate.getForEntity(url, Wishlist.class);
        assertNull(response.getBody());
        System.out.println("Wishlist deleted successfully");
    }

    @Test
    @Order(5)
    void getAllWishlists() {
        ResponseEntity<Wishlist[]> response = restTemplate.getForEntity(baseUrl, Wishlist[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Wishlists: " + List.of(response.getBody()));
    }
}
