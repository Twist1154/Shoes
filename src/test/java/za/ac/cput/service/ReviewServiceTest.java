package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;
import za.ac.cput.factory.ReviewFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ReviewServiceTest {
    @Autowired
    private ReviewService service;

    private Review review;

    private Product product;
    private User user;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        product = productService.read(1L);
        user = userService.read(2L);

        review = ReviewFactory.createReviews(
                null,
                "awesome stuff",
                5,
                product,
                user
        );
        service.create(review);
    }

    @AfterEach
    void tearDown() {
        if (review.getId() != null&& review.getId() > 3){
        service.delete(review.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        Review created = service.create(review);
        assertEquals(review.getId(), created.getId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Review read = service.read(review.getId());
        assertNotNull(read);
        assertEquals(review.getId(), read.getId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Review updated = new Review.Builder()
                .copy(review)
                .setReview("bad stuff")
                .build();
        assertNotNull(service.update(updated));
        assertEquals("bad stuff", updated.getReview());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void findAll() {
        List<Review> reviews = service.findAll();
        assertFalse(reviews.isEmpty());
        System.out.println("All reviews: " + reviews);
    }

    @Test
    @Order(5)
    void delete() {
        boolean deleted = service.delete(review.getId());
        assertTrue(deleted);
        assertNull(service.read(review.getId()));
        System.out.println("Deleted review ID: " + review.getId());
    }

    @Test
    @Order(6)
    void findByProduct_Id() {
        List<Review> reviews = service.findByProduct_Id(product.getId());
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.stream().allMatch(r -> r.getProduct().getId().equals(product.getId())));
        System.out.println("Reviews by product ID: " + reviews);
    }

    @Test
    @Order(7)
    void findByRating() {
        List<Review> reviews = service.findByRating(5);
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.stream().allMatch(r -> r.getRating() == 5));
        System.out.println("Reviews by rating 5: " + reviews);
    }

    @Test
    @Order(8)
    void findByRatingGreaterThan() {
        List<Review> reviews = service.findByRatingGreaterThan(3);
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.stream().allMatch(r -> r.getRating() > 3));
        System.out.println("Reviews with rating greater than 3: " + reviews);
    }
}
