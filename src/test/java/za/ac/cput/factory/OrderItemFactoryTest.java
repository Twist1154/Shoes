package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemFactoryTest {

    private OrderItem orderItem;
    private OrderDetails orderDetails;
    private Product product;
    private ProductSku productSku;

    @BeforeEach
    void setup() {
        Category category = CategoryFactory.createCategory(
                1L,
                "Sneakers"
        );

        PaymentDetails paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                1L,
                100.0,
                "PayPal",
                "Success",
                LocalDateTime.parse("2024-06-12T12:00:00")
        );

        User user = UserFactory.createUser(
                null,
                "avatar.jpg",
                "John",
                "Doe",
                "user1",
                "johndoe@example.com",
                LocalDate.parse("1990-01-01"),
                Set.of(Role.USER, Role.ADMIN),
                "0123456789",
                "password123"
        );

        ImageUrls images = ImageUrlsFactory.createImageUrls(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg"
        );


        orderDetails = OrderDetailsFactory.createOrderDetails(
                1L,
                user,
                paymentDetails,
                300.00
        );

        product = ProductFactory.createProduct(
                1L,
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                images,
                LocalDateTime.now()
        );

        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10"
        );

        ProductAttribute colorAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.COLOR,
                "Green"
        );

        ProductAttribute brandAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.BRAND,
                "Nike"
        );

        productSku = ProductSkuFactory.createProductSku(
                1L,
                product,
                sizeAttribute,
                colorAttribute,
                brandAttribute,
                "SKU-13",
                150.00,
                10
        );

        orderItem = OrderItemFactory.createOrderItem(
                1L,
                orderDetails,
                product,
                productSku,
                2
        );
    }

    @Test
    void testCreateOrderItem() {
        assertNotNull(orderItem);
        System.out.println("Created OrderItem: " + orderItem);
    }

    @Test
    void testCreateOrderItem_WithNullOrderDetails_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> OrderItemFactory.createOrderItem(
                        1L,
                        null,
                        product,
                        productSku,
                        2
                )
        );
        System.out.println("Expected IllegalArgumentException thrown when creating OrderItem with null OrderDetails");
    }

    @Test
    void testCreateOrderItem_WithNullProduct_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> OrderItemFactory.createOrderItem(
                        1L,
                        orderDetails,
                        null,
                        productSku,
                        2
                )
        );
        System.out.println("Expected IllegalArgumentException thrown when creating OrderItem with null Product");
    }

    @Test
    void testCreateOrderItem_WithNullProductSku_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> OrderItemFactory.createOrderItem(
                        1L,
                        orderDetails,
                        product,
                        null,
                        2
                )
        );
        System.out.println("Expected IllegalArgumentException thrown when creating OrderItem with null ProductSkuService");
    }
}
