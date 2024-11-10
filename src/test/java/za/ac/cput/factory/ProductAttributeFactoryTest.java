package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.ProductAttribute;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import za.ac.cput.enums.ProductAttributeType;

class ProductAttributeFactoryTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateProductAttribute() {
        ProductAttribute productAttribute = ProductAttributeFactory.createProductAttribute(
                1L,
                ProductAttributeType.SIZE,
                "10");

        assertNotNull(productAttribute);
        System.out.println("Created ProductAttribute: " + productAttribute);
    }

    @Test
    void testCreateProductAttribute_WithNullType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ProductAttributeFactory.createProductAttribute(
                1L,
                null,
                "10"));

        System.out.println("Expected IllegalArgumentException thrown when creating ProductAttribute with null type");
    }

    @Test
    void testCreateProductAttribute_WithNullValue_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ProductAttributeFactory.createProductAttribute(
                1L,
                ProductAttributeType.COLOR,
                null));

        System.out.println("Expected IllegalArgumentException thrown when creating ProductAttribute with null value");
    }
}
