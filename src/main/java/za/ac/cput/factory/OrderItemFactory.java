package za.ac.cput.factory;

import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.ProductSku;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Factory class for creating instances of {@link OrderItem}.
 * Provides static methods to create {@link OrderItem} objects from various inputs.
 *
 * This version uses only the IDs of associated objects.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */
public class OrderItemFactory {

    /**
     * Creates a {@link OrderItem} instance from various inputs.
     *
     * @param id             the ID of the order item
     * @param orderDetails the ID of the {@link OrderDetails} entity associated with the order item
     * @param product      the ID of the {@link Product} entity associated with the order item
     * @param productSku   the ID of the {@link ProductSku} entity associated with the order item
     * @param quantity       the quantity of the order item
    * @return a new {@link OrderItem} object with properties set from the input parameters
     */
    public static OrderItem createOrderItem(Long id,
                                            OrderDetails orderDetails,
                                            Product product,
                                            ProductSku productSku,
                                            Integer quantity) {
        // Define constants for the switch cases
        final int ORDER_DETAILS_NULL = 1;
        final int PRODUCT_NULL = 2;
        final int PRODUCT_SKU_NULL = 4;
        final int QUANTITY_NULL = 8;

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;

        if (Helper.isNullOrEmpty(orderDetails)) {
            errorFlags |= ORDER_DETAILS_NULL;
        }
        if (Helper.isNullOrEmpty(product)) {
            errorFlags |= PRODUCT_NULL;
        }
        if (Helper.isNullOrEmpty(productSku)) {
            errorFlags |= PRODUCT_SKU_NULL;
        }
        if (Helper.isNullOrEmpty(quantity)) {
            errorFlags |= QUANTITY_NULL;
        }

        // Use switch statement to throw exception based on the flags
        switch (errorFlags) {
            case ORDER_DETAILS_NULL | PRODUCT_NULL | PRODUCT_SKU_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("OrderDetails ID, Product ID, ProductSkuService ID, and Quantity cannot be null");
            case ORDER_DETAILS_NULL | PRODUCT_NULL | PRODUCT_SKU_NULL:
                throw new IllegalArgumentException("OrderDetails ID, Product ID, and ProductSkuService ID cannot be null");
            case ORDER_DETAILS_NULL | PRODUCT_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("OrderDetails ID, Product ID, and Quantity cannot be null");
            case ORDER_DETAILS_NULL | PRODUCT_SKU_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("OrderDetails ID, ProductSkuService ID, and Quantity cannot be null");
            case PRODUCT_NULL | PRODUCT_SKU_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("Product ID, ProductSkuService ID, and Quantity cannot be null");
            case ORDER_DETAILS_NULL | PRODUCT_SKU_NULL:
                throw new IllegalArgumentException("OrderDetails ID and ProductSkuService ID cannot be null");
            case ORDER_DETAILS_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("OrderDetails ID and Quantity cannot be null");
            case PRODUCT_NULL | PRODUCT_SKU_NULL:
                throw new IllegalArgumentException("Product ID and ProductSkuService ID cannot be null");
            case PRODUCT_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("Product ID and Quantity cannot be null");
            case PRODUCT_SKU_NULL | QUANTITY_NULL:
                throw new IllegalArgumentException("ProductSkuService ID and Quantity cannot be null");
            case ORDER_DETAILS_NULL:
                throw new IllegalArgumentException("OrderDetails ID cannot be null");
            case PRODUCT_NULL:
                throw new IllegalArgumentException("Product ID cannot be null");
            case PRODUCT_SKU_NULL:
                throw new IllegalArgumentException("ProductSkuService ID cannot be null");
            case QUANTITY_NULL:
                throw new IllegalArgumentException("Quantity cannot be null");
            default:
                // No null or empty values
                break;
        }


        return new OrderItem.Builder()
                .setId(id)
                .setOrderDetails(orderDetails)
                .setProduct(product)
                .setProductSku(productSku)
                .setQuantity(quantity)
                .build();
    }
}
