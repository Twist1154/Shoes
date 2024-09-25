package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.domain.ProductSku;
import za.ac.cput.factory.ProductSkuFactory;

import java.time.LocalDateTime;
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
        Product product = productService.read(16L);

        // Set up Product attributes (ensure these exist in the DB)
        ProductAttribute size = productAttributeService.read(6L); // Replace with actual ID
        ProductAttribute color = productAttributeService.read(7L);
        ProductAttribute brand = productAttributeService.read(8L);

        // Generate a unique SKU for each test run
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
                10,
                LocalDateTime.now(),
                null
        );

        productSku = productSkuService.create(productSku); // Save the SKU
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
        assertNotNull(productSku);
        assertNotNull(productSku.getId());
        System.out.println("Created: " + productSku);
    }

    @Test
    @Order(2)
    void read() {
        ProductSku readSku = productSkuService.read(3L);
        assertNotNull(readSku);
        assertEquals(2, readSku.getId());
        System.out.println("Read: " + readSku);
    }

    @Test
    @Order(3)
    void update() {
        // Update some attributes
        ProductSku updatedSku = new ProductSku.Builder()
                .copy(productSku)
                .setPrice(120.0)
                .build();
        updatedSku = productSkuService.update(updatedSku);
        assertNotNull(updatedSku);
        assertEquals(120.0, updatedSku.getPrice());
        System.out.println("Updated: " + updatedSku);
    }

    @Test
    @Order(4)
    void findAll() {
        List<ProductSku> skus = productSkuService.findAll();
        assertNotNull(skus);
        assertTrue(skus.size() > 0);
        System.out.println("Found all SKUs: " + skus);
    }

    @Test
    @Order(5)
    void delete() {
        productSkuService.delete(productSku.getId());
        ProductSku deletedSku = productSkuService.read(productSku.getId());
        assertNull(deletedSku);  // The SKU should be deleted and return null
        System.out.println("Deleted SKU with ID: " + productSku.getId());
    }
}
