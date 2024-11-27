package za.ac.cput.factory;

import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

/**
 * Factory class for creating instances of {@link ProductAttribute}.
 * Provides static methods to create {@link ProductAttribute} objects from various inputs.
 *
 * @author Rethabile Ntsekhe
 * @date 24-Aug-24
 */
public class ProductAttributeFactory {

    /**
     * Creates a {@link ProductAttribute} instance from various inputs.
     *
     * @param id          the ID of the product attribute (nullable)
     * @param type        the {@link ProductAttributeType} of the product attribute (e.g., color, size)
     * @param value       the value of the product attribute (cannot be null or empty)
    * @return a new {@link ProductAttribute} object with properties set from the input parameters
     */
    public static ProductAttribute createProductAttribute(Long id,
                                                          ProductAttributeType type,
                                                          String value) {
        // Define constants for validation checks
        final int VALUE_NULL = 1;
        final int TYPE_NULL = 2;

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;

        if (Helper.isNullOrEmpty(value)) {
            errorFlags |= VALUE_NULL;
        }
        if (type == null) {
            errorFlags |= TYPE_NULL;
        }

        // Use switch statement to throw exception based on the flags
        switch (errorFlags) {
            case VALUE_NULL | TYPE_NULL:
                throw new IllegalArgumentException("Type and value cannot be null or empty");
            case VALUE_NULL:
                throw new IllegalArgumentException("Value cannot be null or empty");
            case TYPE_NULL:
                throw new IllegalArgumentException("Type cannot be null");
            default:
                // No null or empty values, proceed
                break;
        }

        // Use the Builder pattern to create a new ProductAttribute object
        return new ProductAttribute.Builder()
                .setId(id)
                .setType(type)
                .setValue(value)
                .build();
    }
}
