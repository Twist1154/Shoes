package za.ac.cput.factory;

import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.domain.ImageUrls;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Factory class for creating instances of {@link Product}.
 * Provides static methods to create {@link Product} objects from various inputs.
 *
 * author: Rethabile Ntsekhe
 * date: 25-Aug-24
 */
public class ProductFactory {

    /**
     * Creates a {@link Product} instance from various inputs.
     *
     * @param id          the ID of the product
     * @param name        the name of the product
     * @param description the description of the product
     * @param summary     the summary of the product
     * @param cover       the cover image path of the product
     * @param imageUrls   the object containing image URLs of the product
     * @param createdAt   the date and time the product was created
     * @return a new {@link Product} object with properties set from the input parameters
     */
    public static Product createProduct(Long id, String name, String description, String summary, String cover, ImageUrls imageUrls,  LocalDateTime createdAt) {
        // Define constants for the switch cases
        final int NAME_NULL = 1;
        final int DESCRIPTION_NULL = 2;
        final int SUMMARY_NULL = 4;
        final int COVER_NULL = 8;

        // Calculate the errorFlags based on null checks
        int errorFlags = 0;

        if (Helper.isNullOrEmpty(name)) {
            errorFlags |= NAME_NULL;
        }
        if (Helper.isNullOrEmpty(description)) {
            errorFlags |= DESCRIPTION_NULL;
        }
        if (Helper.isNullOrEmpty(summary)) {
            errorFlags |= SUMMARY_NULL;
        }
        if (Helper.isNullOrEmpty(cover)) {
            errorFlags |= COVER_NULL;
        }

        // Use if-else to throw exceptions based on the flags
        if (errorFlags != 0) {
            StringBuilder errorMessage = new StringBuilder("The following fields cannot be null: ");
            if ((errorFlags & NAME_NULL) != 0) errorMessage.append("name, ");
            if ((errorFlags & DESCRIPTION_NULL) != 0) errorMessage.append("description, ");
            if ((errorFlags & SUMMARY_NULL) != 0) errorMessage.append("summary, ");
            if ((errorFlags & COVER_NULL) != 0) errorMessage.append("cover, ");

            // Remove trailing comma and space
            errorMessage.setLength(errorMessage.length() - 2);

            throw new IllegalArgumentException(errorMessage.toString());
        }

        // Use the Builder pattern to create a new Product object
        return new Product.Builder()
                .setId(id) // Set the ID of the product
                .setName(name) // Set the name of the product
                .setDescription(description) // Set the description of the product
                .setSummary(summary) // Set the summary of the product
                .setCover(cover) // Set the cover of the product
                .setImageUrls(imageUrls) // Set the image URLs of the product
                .setCreatedAt(createdAt) // Set the date the product was created
                .build();
    }
}