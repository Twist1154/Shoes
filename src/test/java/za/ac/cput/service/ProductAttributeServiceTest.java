package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.factory.ProductAttributeFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductAttributeServiceTest {

    @Autowired
    private ProductAttributeService service;

    private ProductAttribute productAttribute;

    @BeforeEach
    void setUp() {
        // Set up Product attributes
        productAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10",
                LocalDateTime.now(),
                null
        );
        productAttribute = service.create(productAttribute);
    }

    @AfterEach
    void tearDown() {
        /*if (productAttribute != null && productAttribute.getId() != null) {
            service.delete(productAttribute.getId());
        }*/
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(productAttribute);
        assertNotNull(productAttribute.getId());
        System.out.println("Created: " + productAttribute);
    }

    @Test
    @Order(2)
    void read() {
        ProductAttribute readAttribute = service.read(productAttribute.getId());
        assertNotNull(readAttribute);
        assertEquals(productAttribute.getId(), readAttribute.getId());
        System.out.println("Read: " + readAttribute);
    }

    @Test
    @Order(3)
    void update() {
        productAttribute = new ProductAttribute.Builder()
                .copy(productAttribute)
                .setValue("Updated Size")
                .build();
        ProductAttribute updatedAttribute = service.update(productAttribute);
        assertNotNull(updatedAttribute);
        assertEquals("Updated Size", updatedAttribute.getValue());
        System.out.println("Updated: " + updatedAttribute);
    }

    @Test
    @Order(4)
    void findAll() {
        List<ProductAttribute> attributes = service.findAll();
        assertNotNull(attributes);
        assertFalse(attributes.isEmpty());
        System.out.println("Found all attributes: " + attributes);
    }

    @Test
    @Order(5)
    void delete() {
        service.delete(productAttribute.getId());
        ProductAttribute deletedAttribute = service.read(productAttribute.getId());
        assertNull(deletedAttribute);
        System.out.println("Deleted attribute with ID: " + productAttribute.getId());
    }
}
