package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.*;
import za.ac.cput.factory.ProductSkuFactory;
import za.ac.cput.service.ProductAttributeService;
import za.ac.cput.service.ProductService;
import za.ac.cput.service.ProductSkuService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductSkuControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductAttributeService productAttributeService;

    private ProductSku productSku;

    private Product product;

    private final String baseUrl = "http://localhost:8080/store/product-skus";

    @BeforeEach
    void setUp() {
        // Read Product from DB (make sure it exists)
        product = productService.read(29L);
        assertNotNull(product, "Product should not be null");

        // Read attributes from DB (ensure they exist)
        ProductAttribute size = productAttributeService.read(6L); // Replace with valid IDs
        ProductAttribute color = productAttributeService.read(7L);
        ProductAttribute brand = productAttributeService.read(8L);

        assertNotNull(size, "Size attribute should not be null");
        assertNotNull(color, "Color attribute should not be null");
        assertNotNull(brand, "Brand attribute should not be null");

        // Generate a unique SKU for each test run
        String uniqueSku = "SKU-" + System.currentTimeMillis();

        // Create Product SKU
        productSku = ProductSkuFactory.createProductSku(
                40L,
                product,
                size,
                color,
                brand,
                uniqueSku,
                100.0,
                10
        );
    }

    @AfterEach
    void tearDown() {
        /*if (productSku != null && productSku.getId() != null) {
            productSkuService.delete(productSku.getId());
        }*/
    }

    @Test
    @Order(1)
    void createProductSku() {
        // Create Product SKU
        ResponseEntity<ProductSku> response = restTemplate.postForEntity(baseUrl + "/create", productSku, ProductSku.class);
        System.out.println(response.getBody());
        /*productSku = new ProductSku.Builder().copy(response.getBody())
                .setId(response.getBody().getId())
                .build();*/

        assertNotNull(response.getBody());
        productSku = response.getBody(); // Store created productSku for later tests
    }

    @Test
    @Order(2)
    void getProductSkuById() {
        // Ensure productSku is created before fetching
        assertNotNull(productSku, "ProductSku must be created before testing fetch by ID");

        ResponseEntity<ProductSku> response = restTemplate.getForEntity(baseUrl + "/read/" + 30, ProductSku.class);
        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        assertEquals(30, response.getBody().getId());
    }

    @Test
    @Order(3)
    void updateProductSku() {
        // Retrieve ProductSku directly from the database via REST API call
        ProductSku productSku = restTemplate.getForObject(baseUrl + "/read/" + 42, ProductSku.class);
        assertNotNull(productSku, "ProductSku must exist before testing update");

        System.out.println(productSku);
        System.out.println("\n Product Sku to be updated: -------------------");

        // Modify the retrieved ProductSku with updated details
        ProductSku updatedProductSku = new ProductSku.Builder()
                .copy(productSku)
                .setId(productSku.getId()) // ID stays the same
                .setProduct(productSku.getProduct()) // Product stays the same
                .setSizeAttribute(productSku.getSizeAttribute()) // Size attribute stays the same
                .setColorAttribute(productSku.getColorAttribute()) // Color attribute stays the same
                .setBrandAttribute(productSku.getBrandAttribute()) // Brand attribute stays the same
                .setSku(productSku.getSku()) // SKU stays the same
                .setPrice(257.0) // Update the price
                .setQuantity(productSku.getQuantity()) // Quantity stays the same
                .build();

        System.out.println(updatedProductSku);
        System.out.println("\n Updated Product Sku: -------------------");

        // Send the PUT request to update the ProductSku
        HttpEntity<ProductSku> requestUpdate = new HttpEntity<>(updatedProductSku);
        ResponseEntity<ProductSku> response = restTemplate.exchange(
                baseUrl + "/update/" + updatedProductSku.getId(),
                HttpMethod.PUT,
                requestUpdate,
                ProductSku.class
        );

        System.out.println(response.getBody());

        // Verify that the update was successful
        assertNotNull(response.getBody(), "Updated ProductSku should not be null");
        assertEquals(42, response.getBody().getId(), "The ProductSku ID should remain unchanged");
        assertEquals(257.0, response.getBody().getPrice(), "The ProductSku price should be updated to 257.0");
    }



    @Test
    @Order(4)
    void getAllProductSkus() {
        // Fetch all product SKUs
        ResponseEntity<ProductSku[]> response = restTemplate.getForEntity(baseUrl + "/all", ProductSku[].class);
        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0, "Product SKUs should not be empty");
    }

    @Test
    @Order(5)
    void deleteProductSku() {
        // Ensure productSku is created before deleting
        assertNotNull(productSku, "ProductSku must be created before testing delete");
        assertNotNull(productSku.getId(), "ProductSku ID must not be null");

        // Delete the product SKU
        restTemplate.delete(baseUrl + "/delete/" + productSku.getId());

        // Attempt to fetch the deleted product SKU
        ResponseEntity<ProductSku> deletedProductSku = restTemplate.getForEntity(baseUrl + "/read/" + productSku.getId(), ProductSku.class);
        assertNull(deletedProductSku.getBody(), "Deleted product SKU should not be found");
    }
}
