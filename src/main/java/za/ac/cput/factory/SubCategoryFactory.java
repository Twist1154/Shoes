package za.ac.cput.factory;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.domain.ProductSubCategories;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Factory class for creating instances of {@link SubCategory}.
 * Provides static methods to create {@link SubCategory} objects from various inputs.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
public class SubCategoryFactory {

    /**
     * Creates a {@link SubCategory} instance from various inputs.
     *
     * @param id          the ID of the sub-category
     * @param category    the parent {@link Category} entity associated with this sub-category
     * @param productSubCategories the list of {@link ProductSubCategories} associated with this sub-category
     * @param name        the name of the sub-category
     * @param description the description of the sub-category
     * @param createdAt   the date the sub-category was created
     * @param deletedAt   the date the sub-category was deleted (if applicable)
     * @return a new {@link SubCategory} object with properties set from the input parameters
     */
    public static SubCategory createSubCategory(Long id,
                                                Category category,
                                                List<ProductSubCategories> productSubCategories,
                                                String name,
                                                String description,
                                                LocalDateTime createdAt,
                                                LocalDateTime deletedAt) {
        // Define constants for the switch cases
        final int NAME_NULL = 1;
        final int DESCRIPTION_NULL = 2;
        final int CATEGORY_NULL = 4;
        final int PRODUCT_SUBCATEGORY_NULL = 8;

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;

        if (Helper.isNullOrEmpty(name)) {
            errorFlags |= NAME_NULL;
        }
        if (Helper.isNullOrEmpty(description)) {
            errorFlags |= DESCRIPTION_NULL;
        }
        if (category == null) {
            errorFlags |= CATEGORY_NULL;
        }
        if (productSubCategories == null || productSubCategories.isEmpty()) { // Check if productSubCategories is null or empty
            errorFlags |= PRODUCT_SUBCATEGORY_NULL;
        }

        // Use switch statement to throw exception based on the flags
        if (errorFlags != 0) {
            StringBuilder errorMessage = new StringBuilder("The following fields cannot be null: ");
            if ((errorFlags & NAME_NULL) != 0) errorMessage.append("name, ");
            if ((errorFlags & DESCRIPTION_NULL) != 0) errorMessage.append("description, ");
            if ((errorFlags & CATEGORY_NULL) != 0) errorMessage.append("category, ");
            if ((errorFlags & PRODUCT_SUBCATEGORY_NULL) != 0) errorMessage.append("productSubCategories");

            // Remove trailing comma and space
            errorMessage.setLength(errorMessage.length() - 2);

            throw new IllegalArgumentException(errorMessage.toString());
        }

        // Use the Builder pattern to create a new SubCategory object
        return new SubCategory.Builder()
                .setId(id) // Set the ID of the sub-category
                .setCategory(category) // Set the parent category associated with the sub-category
                .setProductSubCategories(productSubCategories) // Set the list of product subcategories associated with the sub-category
                .setName(name) // Set the name of the sub-category
                .setDescription(description) // Set the description of the sub-category
                .setCreatedAt(createdAt) // Set the date the sub-category was created
                .setDeletedAt(deletedAt) // Set the date the sub-category was deleted (if applicable)
                .build();
    }
}
