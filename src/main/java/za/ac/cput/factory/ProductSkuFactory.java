package za.ac.cput.factory;

import za.ac.cput.domain.Product;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.domain.ProductSku;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

/**
 * Factory class for creating instances of {@link ProductSku}.
 * Provides static methods to create {@link ProductSku} objects from various inputs.
 *
 * @author Rethabile
 * @date 25-Aug-24
 */
public class ProductSkuFactory {

    /**
     * Creates a {@link ProductSku} instance from various inputs.
     *
     * @param id              the ID of the product SKU
     * @param product         the {@link Product} entity associated with this product SKU
     * @param sizeAttribute   the size {@link ProductAttribute} of the SKU
     * @param colorAttribute  the color {@link ProductAttribute} of the SKU
     * @param brandAttribute  the brand {@link ProductAttribute} of the SKU
     * @param sku             the SKU code of the product
     * @param price           the price of the product SKU
     * @param quantity        the quantity of the product SKU
    * @return a new {@link ProductSku} object with properties set from the input parameters
     */
    public static ProductSku createProductSku(Long id,
                                              Product product,
                                              ProductAttribute sizeAttribute,
                                              ProductAttribute colorAttribute,
                                              ProductAttribute brandAttribute,
                                              String sku,
                                              double price,
                                              int quantity
    ) {

        // Define constants for validation flags
        final int PRODUCT_NULL = 1;
        final int SIZE_ATTRIBUTE_NULL = 2;
        final int COLOR_ATTRIBUTE_NULL = 4;
        final int BRAND_ATTRIBUTE_NULL = 8;
        final int SKU_NULL = 16;
        final int PRICE_INVALID = 32;
        final int QUANTITY_INVALID = 64;

        // Calculate error flags based on null checks and invalid fields
        int errorFlags = 0;

        if (product == null) {
            errorFlags |= PRODUCT_NULL;
        }
        if (sizeAttribute == null) {
            errorFlags |= SIZE_ATTRIBUTE_NULL;
        }
        if (colorAttribute == null) {
            errorFlags |= COLOR_ATTRIBUTE_NULL;
        }
        if (brandAttribute == null) {
            errorFlags |= BRAND_ATTRIBUTE_NULL;
        }
        if (Helper.isNullOrEmpty(sku)) {
            errorFlags |= SKU_NULL;
        }
        if (price <= 0) {
            errorFlags |= PRICE_INVALID;
        }
        if (quantity <= 0) {
            errorFlags |= QUANTITY_INVALID;
        }

        // Use switch statement to throw exceptions based on the validation flags
        switch (errorFlags) {
            case PRODUCT_NULL | SIZE_ATTRIBUTE_NULL | COLOR_ATTRIBUTE_NULL | BRAND_ATTRIBUTE_NULL | SKU_NULL | PRICE_INVALID | QUANTITY_INVALID:
                throw new IllegalArgumentException("Product, size, color, and brand attributes cannot be null, SKU cannot be null or empty, price must be greater than zero, and quantity must be greater than zero");
            case PRODUCT_NULL | SIZE_ATTRIBUTE_NULL | COLOR_ATTRIBUTE_NULL | BRAND_ATTRIBUTE_NULL | SKU_NULL:
                throw new IllegalArgumentException("Product, size, color, and brand attributes cannot be null, and SKU cannot be null or empty");
            case PRODUCT_NULL:
                throw new IllegalArgumentException("Product cannot be null");
            case SIZE_ATTRIBUTE_NULL:
                throw new IllegalArgumentException("Size attribute cannot be null");
            case COLOR_ATTRIBUTE_NULL:
                throw new IllegalArgumentException("Color attribute cannot be null");
            case BRAND_ATTRIBUTE_NULL:
                throw new IllegalArgumentException("Brand attribute cannot be null");
            case SKU_NULL:
                throw new IllegalArgumentException("SKU cannot be null or empty");
            case PRICE_INVALID:
                throw new IllegalArgumentException("Price must be greater than zero");
            case QUANTITY_INVALID:
                throw new IllegalArgumentException("Quantity must be greater than zero");
            default:
                // No null or invalid values
                break;
        }

        // Use the Builder pattern to create a new ProductSkuService object
        return new ProductSku.Builder()
                .setId(id)
                .setProduct(product)
                .setSizeAttribute(sizeAttribute)
                .setColorAttribute(colorAttribute)
                .setBrandAttribute(brandAttribute)
                .setSku(sku)
                .setPrice(price)
                .setQuantity(quantity)
                .build();
    }
}
