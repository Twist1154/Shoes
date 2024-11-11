package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.factory.ProductAttributeFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductAttributeServiceTest {

    @Autowired
    private ProductAttributeService service;

    private ProductAttribute size, color, brand;

    @BeforeEach
    void setUp() {
        // Create unique Product attributes for each test
        size = service.create(ProductAttributeFactory.createProductAttribute(
                null, ProductAttributeType.SIZE, "10"
        ));
        color = service.create(ProductAttributeFactory.createProductAttribute(
                null, ProductAttributeType.COLOR, "Red"
        ));
        brand = service.create(ProductAttributeFactory.createProductAttribute(
                null, ProductAttributeType.BRAND, "Nike"
        ));
    }

    @AfterEach
    void tearDown() {
        // Clean up all test data after each test
        if (size != null && size.getId() != null) {
            service.delete(size.getId());
        }
        if (color != null && color.getId() != null) {
            service.delete(color.getId());
        }
        if (brand != null && brand.getId() != null) {
            service.delete(brand.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(size);
        assertNotNull(color);
        assertNotNull(brand);

        assertNotNull(size.getId());
        assertNotNull(color.getId());
        assertNotNull(brand.getId());

        System.out.println("Created Size: " + size);
        System.out.println("Created Color: " + color);
        System.out.println("Created Brand: " + brand);
    }

    @Test
    @Order(2)
    void read() {
        ProductAttribute readSize = service.read(size.getId());
        ProductAttribute readColor = service.read(color.getId());
        ProductAttribute readBrand = service.read(brand.getId());

        assertNotNull(readSize);
        assertNotNull(readColor);
        assertNotNull(readBrand);

        assertEquals(size.getId(), readSize.getId());
        assertEquals(color.getId(), readColor.getId());
        assertEquals(brand.getId(), readBrand.getId());

        System.out.println("Read Size: " + readSize);
        System.out.println("Read Color: " + readColor);
        System.out.println("Read Brand: " + readBrand);
    }

    @Test
    @Order(3)
    void update() {
        size = new ProductAttribute.Builder()
                .copy(size)
                .setValue("Updated Size")
                .build();

        color = new ProductAttribute.Builder()
                .copy(color)
                .setValue("Updated Color")
                .build();

        brand = new ProductAttribute.Builder()
                .copy(brand)
                .setValue("Updated Brand")
                .build();

        ProductAttribute updatedSize = service.update(size);
        ProductAttribute updatedColor = service.update(color);
        ProductAttribute updatedBrand = service.update(brand);

        assertNotNull(updatedSize);
        assertNotNull(updatedColor);
        assertNotNull(updatedBrand);

        assertEquals("Updated Size", updatedSize.getValue());
        assertEquals("Updated Color", updatedColor.getValue());
        assertEquals("Updated Brand", updatedBrand.getValue());

        System.out.println("Updated Size: " + updatedSize);
        System.out.println("Updated Color: " + updatedColor);
        System.out.println("Updated Brand: " + updatedBrand);
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
        service.delete(size.getId());
        service.delete(color.getId());
        service.delete(brand.getId());

        ProductAttribute deletedSize = service.read(size.getId());
        ProductAttribute deletedColor = service.read(color.getId());
        ProductAttribute deletedBrand = service.read(brand.getId());

        assertNull(deletedSize);
        assertNull(deletedColor);
        assertNull(deletedBrand);

        System.out.println("Deleted attribute with ID: " + size.getId());
        System.out.println("Deleted attribute with ID: " + color.getId());
        System.out.println("Deleted attribute with ID: " + brand.getId());
    }
}
