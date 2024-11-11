package za.ac.cput.factory;

import org.hibernate.procedure.ProcedureOutputs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.enums.Role;

import java.awt.*;
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
    public  Product product;
    private ProductSku productSku;
    private ImageUrls images;
    private List<SubCategory> subCategory;
    private Category category;
    private User user;
    private PaymentDetails paymentDetails;
    private Set<Role> roles;

    @BeforeEach
    void setup() {
        // Create a sample Category object using the factory method
        category = CategoryFactory.createCategory(
                1L,
                "Sneakers"
        );
        // Set up a sample PaymentDetails object using the factory method
        paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                1L,
                orderDetails,
                100.0,
                "PayPal",
                "Success",
                LocalDateTime.parse("2024-06-12T12:00:00")
        );

        // Create a sample User object using the factory method
        roles = new HashSet<>(Set.of(Role.USER, Role.ADMIN));

        // Set up a sample User object using the factory method
        user = UserFactory.createUser(
                null,
                "avatar.jpg",
                "John",
                "Doe",
                "user1",
                "johndoe@example.com",
                LocalDate.parse("1990-01-01"),
                roles,
                "0123456789",
                "password123");

        // Create a sample ImageUrls object using the factory method
        images = ImageUrlsFactory.createImageUrls(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg"
        );

        // Create sample SubCategory objects using the factory method
        SubCategory subCategory1 = SubCategoryFactory.createSubCategory(
                1L,
                category,
                product
                );

        SubCategory subCategory2 = SubCategoryFactory.createSubCategory(
                2L,
                category,
                product
        );

        subCategory = List.of(subCategory1, subCategory2);


        // Set up sample OrderDetails, Product, and ProductSkuService objects
        orderDetails = OrderDetailsFactory.createOrderDetails(
                1L,
                user,
                paymentDetails,
                300.00
        );

        // Create a sample Product object using the factory method
        product = ProductFactory.createProduct(
                1L,
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                images,
                subCategory,
                LocalDateTime.now()
        );

        // Create sample ProductAttribute objects for size, color, and brand
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

        // Set up a sample OrderItem object using the factory method
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
        // Verify that the OrderItem object is not null
        assertNotNull(orderItem);

        // Print the created OrderItem object to the terminal
        System.out.println("Created OrderItem: " + orderItem);
    }

    @Test
    void testCreateOrderItem_WithNullOrderDetails_ThrowsIllegalArgumentException() {
        // Try to create an OrderItem object with null OrderDetails
        assertThrows(IllegalArgumentException.class,
                () -> OrderItemFactory.createOrderItem(
                        1L,
                        null,
                        product,
                        productSku,
                        2
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating OrderItem with null OrderDetails");
    }

    @Test
    void testCreateOrderItem_WithNullProduct_ThrowsIllegalArgumentException() {
        // Try to create an OrderItem object with null Product
        assertThrows(IllegalArgumentException.class,
                () -> OrderItemFactory.createOrderItem(
                        1L,
                        orderDetails,
                        null,
                        productSku,
                        2
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating OrderItem with null Product");
    }

    @Test
    void testCreateOrderItem_WithNullProductSku_ThrowsIllegalArgumentException() {
        // Try to create an OrderItem object with null ProductSkuService
        assertThrows(IllegalArgumentException.class,
                () -> OrderItemFactory.createOrderItem(
                        1L,
                        orderDetails,
                        product,
                        null,
                        2
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating OrderItem with null ProductSkuService");
    }
}