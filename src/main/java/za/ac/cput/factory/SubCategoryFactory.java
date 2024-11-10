package za.ac.cput.factory;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.util.Helper;

/**
 * Factory class for creating instances of {@link SubCategory}.
 * Provides static methods to create {@link SubCategory} objects from various inputs.
 * <p>
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
public class SubCategoryFactory {

    /**
     * Creates a {@link SubCategory} instance from various inputs.
     *
     * @param id       the ID of the sub-category
     * @param category the parent {@link Category} entity associated with this sub-category
     * @return a new {@link SubCategory} object with properties set from the input parameters
     */
    public static SubCategory createSubCategory(Long id,
                                                Category category,
                                                Product product) {
        // Define constants for the switch cases;
        final int CATEGORY_NULL = 1; // Removed DESCRIPTION_NULL since it's not in SubCategory

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;


        if (category == null) { // Check directly for null as category is a non-nullable field
            errorFlags |= CATEGORY_NULL;
        }

        // Use switch statement to throw exception based on the flags
        switch (errorFlags) {
            case CATEGORY_NULL:
                throw new IllegalArgumentException("Category cannot be null or empty");
            default:
                // No null or empty values
                break;
        }

        return new SubCategory.Builder()
                .setId(id)
                .setCategory(category)
                .setProduct(product)
                .build();
    }
}
