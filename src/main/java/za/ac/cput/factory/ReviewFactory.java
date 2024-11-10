package za.ac.cput.factory;

import za.ac.cput.domain.Product;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

/**
 * ReviewFactory.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 24-Sep-24
 */

public class ReviewFactory {

    public static Review createReviews(Long id,
                                       String review,
                                       int rating,
                                       Product product,
                                       User user) {
        // Define constants for the switch cases
        final int REVIEW_NULL = 1;
        final int RATING_INVALID = 2;
        final int PRODUCT_NULL = 4;
        final int CREATED_AT_NULL = 8;

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;

        // Check for null or empty values for required parameters
        if (Helper.isNullOrEmpty(review)) {
            errorFlags |= REVIEW_NULL;
        }
        if (rating < 1 || rating > 5) {
            errorFlags |= RATING_INVALID; // Ratings should be between 1 and 5
        }
        if (product == null) {
            errorFlags |= PRODUCT_NULL;
        }


        // Use switch statement to throw exception based on the flags
        switch (errorFlags) {
            case REVIEW_NULL | RATING_INVALID | PRODUCT_NULL | CREATED_AT_NULL:
                throw new IllegalArgumentException("Review, rating, product, and created date cannot be null or invalid");
            case REVIEW_NULL | RATING_INVALID | PRODUCT_NULL:
                throw new IllegalArgumentException("Review, rating, and product cannot be null or invalid");
            case REVIEW_NULL | RATING_INVALID:
                throw new IllegalArgumentException("Review and rating cannot be null or invalid");
            case REVIEW_NULL | PRODUCT_NULL:
                throw new IllegalArgumentException("Review and product cannot be null");
            case RATING_INVALID | PRODUCT_NULL:
                throw new IllegalArgumentException("Rating is invalid, and product cannot be null");
            case REVIEW_NULL:
                throw new IllegalArgumentException("Review cannot be null or empty");
            case RATING_INVALID:
                throw new IllegalArgumentException("Rating is invalid (should be between 1 and 5)");
            case PRODUCT_NULL:
                throw new IllegalArgumentException("Product cannot be null");
            default:
                // No null or empty values
                break;
        }

        // Build and return the Review object
        return new Review.Builder()
                .setId(id)
                .setReview(review)
                .setRating(rating)
                .setProduct(product)
                .setUser(user)
                .build();
    }
}
