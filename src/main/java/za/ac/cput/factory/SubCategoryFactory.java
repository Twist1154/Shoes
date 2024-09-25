package za.ac.cput.factory;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
     * @param name        the name of the sub-category
     * @param description the description of the sub-category
     * @param createdAt   the date the sub-category was created
     * @param deletedAt   the date the sub-category was deleted (if applicable)
     * @return a new {@link SubCategory} object with properties set from the input parameters
     */
    public static SubCategory createSubCategory(Long id,
                                                Category category,
                                                String name,
                                                String description,
                                                LocalDateTime createdAt,
                                                LocalDateTime deletedAt) {
        // Define constants for the switch cases
        final int NAME_NULL = 1;
        final int DESCRIPTION_NULL = 2;
        final int CATEGORY_NULL = 4;

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;

        if (Helper.isNullOrEmpty(name)) {
            errorFlags |= NAME_NULL;
        }
        if (Helper.isNullOrEmpty(description)) {
            errorFlags |= DESCRIPTION_NULL;
        }
        if (Helper.isNullOrEmpty(category)) {
            errorFlags |= CATEGORY_NULL;
        }

        // Use switch statement to throw exception based on the flags
        switch (errorFlags) {
            case NAME_NULL | DESCRIPTION_NULL | CATEGORY_NULL:
                throw new IllegalArgumentException("Name, description, and category cannot be null or empty");
            case NAME_NULL | DESCRIPTION_NULL:
                throw new IllegalArgumentException("Name and description cannot be null or empty");
            case NAME_NULL | CATEGORY_NULL:
                throw new IllegalArgumentException("Name and category cannot be null or empty");
            case DESCRIPTION_NULL | CATEGORY_NULL:
                throw new IllegalArgumentException("Description and category cannot be null or empty");
            case NAME_NULL:
                throw new IllegalArgumentException("Name cannot be null or empty");
            case DESCRIPTION_NULL:
                throw new IllegalArgumentException("Description cannot be null or empty");
            case CATEGORY_NULL:
                throw new IllegalArgumentException("Category cannot be null or empty");
            default:
                // No null or empty values
                break;
        }

        // Use the Builder pattern to create a new SubCategory object
        return new SubCategory.Builder()
                .setId(id) // Set the ID of the sub-category
                .setCategory(category) // Set the parent category associated with the sub-category
                .setName(name) // Set the name of the sub-category
                .setDescription(description) // Set the description of the sub-category
                .setCreatedAt(createdAt) // Set the date the sub-category was created
                .setDeletedAt(deletedAt) // Set the date the sub-category was deleted (if applicable)
                .build();
    }
}