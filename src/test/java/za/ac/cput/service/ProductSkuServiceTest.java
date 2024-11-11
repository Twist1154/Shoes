package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.domain.ProductSku;
import za.ac.cput.factory.ProductSkuFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductSkuServiceTest {

    @Autowired
    private ProductSkuService productSkuService;

    private ProductSku productSku;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAttributeService productAttributeService;

    @BeforeEach
    void setUp() {
        // Load existing product and attributes from DB (make sure these IDs exist in your test data)
        Product product = productService.read(1L);
        ProductAttribute size = productAttributeService.read(1L);
        ProductAttribute color = productAttributeService.read(2L);
        ProductAttribute brand = productAttributeService.read(3L);

        // Generate a unique SKU
        String uniqueSku = "SKU-" + System.currentTimeMillis();

        // Create Product SKU
        productSku = ProductSkuFactory.createProductSku(
                null,
                product,
                size,
                color,
                brand,
                uniqueSku,
                100.0,
                10
        );

        // Save the Product SKU
        productSku = productSkuService.create(productSku);
    }

    @AfterEach
    void tearDown() {
        if (productSku != null && productSku.getId() != null) {
            productSkuService.delete(productSku.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(productSku, "Product SKU should not be null after creation");
        assertNotNull(productSku.getId(), "Product SKU ID should be generated");
        System.out.println("Created: " + productSku);
    }

    @Test
    @Order(2)
    void read() {
        ProductSku productSku1 = productSkuService.create(productSku);
        ProductSku readSku = productSkuService.read(productSku1.getId());
        assertNotNull(readSku, "Read SKU should not be null");
        assertEquals(productSku1.getId(), readSku.getId(), "Read SKU ID should match created SKU ID");
        System.out.println("Read: " + readSku);
    }

    @Test
    @Order(3)
    void update() {
        ProductSku updatedSku = new ProductSku.Builder()
                .copy(productSku)
                .setPrice(120.0)
                .build();
        updatedSku = productSkuService.update(updatedSku);

        assertNotNull(updatedSku, "Updated SKU should not be null");
        assertEquals(120.0, updatedSku.getPrice(), "Updated SKU price should be 120.0");
        System.out.println("Updated: " + updatedSku);
    }

    @Test
    @Order(4)
    void findAll() {
        List<ProductSku> skus = productSkuService.findAll();
        assertNotNull(skus, "Find all SKUs should not return null");
        assertFalse(skus.isEmpty(), "Find all SKUs should return at least one SKU");
        System.out.println("Found all SKUs: " + skus);
    }

    @Test
    @Order(5)
    void delete() {
        productSkuService.delete(productSku.getId());
        ProductSku deletedSku = productSkuService.read(productSku.getId());
        assertNull(deletedSku, "Deleted SKU should be null when read");
        System.out.println("Deleted SKU with ID: " + productSku.getId());
    }
}
