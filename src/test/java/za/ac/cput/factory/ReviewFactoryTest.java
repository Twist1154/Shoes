package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ReviewFactoryTest.java
 *
 * Test for ReviewFactory
 *
 * @author Rethabile
 * @date 24-Sep-24
 */

class ReviewFactoryTest {

    @Test
    void createValidReview() {
        User user = new User();
        // Create a valid product for the review
        Product product = new Product.Builder()
                .setId(1L)
                .setName("Test Product")
                .setDescription("Test Description")
                .setCover("Test Cover")
                .build();

        // Test valid creation
        Review review = ReviewFactory.createReviews(
                1L,
                "Great product",
                5,
                product,
                user
        );

        assertNotNull(review);
        assertEquals(1L, review.getId());
        assertEquals("Great product", review.getReview());
        assertEquals(5, review.getRating());
        assertEquals(product, review.getProduct());
    }

    @Test
    void createReviewWithNullReview() {
        User user = new User();
        // Create a valid product for the review
        Product product = new Product.Builder()
                .setId(1L)
                .setName("Test Product")
                .setDescription("Test Description")
                .setName("Test Name")
                .build();

        // Test null review
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ReviewFactory.createReviews(
                        1L,
                        null,
                        5,
                        product,
                        user
                )
        );

        assertEquals("Review cannot be null or empty", exception.getMessage());
    }

    @Test
    void createReviewWithInvalidRating() {
        User user = new User();
        // Create a valid product for the review
        Product product = new Product.Builder()
                .setId(1L)
                .setName("Test Product")
                .setDescription("Test Description")
                .setName("Test Name")
                .build();

        // Test invalid rating
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ReviewFactory.createReviews(
                        1L,
                        "Good product",
                        0,
                        product,
                        user
                )
        );

        assertEquals("Rating is invalid (should be between 1 and 5)", exception.getMessage());
    }

    @Test
    void createReviewWithNullProduct() {
        User user = new User();
        // Test null product
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ReviewFactory.createReviews(
                        1L,
                        "Good product",
                        5,
                        null,
                        user
                )
        );

        assertEquals("Product cannot be null", exception.getMessage());
    }

}
