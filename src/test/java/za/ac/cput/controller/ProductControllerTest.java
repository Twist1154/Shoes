package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.ImageUrls;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.factory.ImageUrlsFactory;
import za.ac.cput.factory.ProductFactory;
import za.ac.cput.service.ProductService;
import za.ac.cput.service.SubCategoryService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private SubCategoryService subCategoryService;

    private Product product;
    private SubCategory subCategory;
    private ImageUrls image;

    private final String baseUrl = "http://localhost:8080/products";

    @BeforeEach
    void setUp() {
        subCategory = subCategoryService.read(28L);
        assertNotNull(subCategory, "SubCategory should not be null");

        image = ImageUrlsFactory.createImageUrls(
                "null",
                "cover image url",
                "thumbnail image url",
                "null"
        );

        product = ProductFactory.createProduct(
                null,
                "product Controller test",
                "product Controller create test",
                "product Controller create test",
                "cover image url",
                image,
                Collections.singletonList(subCategory),
                LocalDateTime.now(),
                null
        );
    }

    @AfterEach
    void tearDown() {
        if (product != null && product.getId() != null) {
            productService.delete(product.getId());
        }
    }

    @Test
    @Order(1)
    void createProduct() {
        // Create a new Product via the REST API
        ResponseEntity<Product> response = restTemplate.postForEntity(baseUrl + "/create", product, Product.class);
        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        product = response.getBody(); // Store the created product for future tests
    }

    @Test
    @Order(2)
    void getProductById() {
        assertNotNull(product, "Product must be created before testing get by ID");

        // Fetch the Product by ID
        ResponseEntity<Product> response = restTemplate.getForEntity(baseUrl + "/read/" + product.getId(), Product.class);
        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        assertEquals(product.getId(), response.getBody().getId());
    }

    @Test
    @Order(3)
    void updateProduct() {
        // Modify the product details
        product = new Product.Builder()
                .copy(product)
                .setName("Updated Product Name")
                .build();
        HttpEntity<Product> requestUpdate = new HttpEntity<>(product);

        // Send the PUT request to update the product
        ResponseEntity<Product> response = restTemplate.exchange(
                baseUrl + "/update/" + product.getId(),
                HttpMethod.PUT,
                requestUpdate,
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals("Updated Product Name", response.getBody().getName());
    }

    @Test
    @Order(4)
    void deleteProduct() {
        assertNotNull(product, "Product must be created before testing delete");
        assertNotNull(product.getId(), "Product ID must not be null");

        // Delete the product by ID
        restTemplate.delete(baseUrl + "/delete/" + product.getId());

        // Attempt to fetch the deleted product
        ResponseEntity<Product> deletedProduct = restTemplate.getForEntity(baseUrl + "/read/" + product.getId(), Product.class);
        assertNull(deletedProduct.getBody(), "Deleted product should not be found");
    }

    @Test
    @Order(5)
    void getAllProducts() {
        // Fetch all products
        ResponseEntity<Product[]> response = restTemplate.getForEntity(baseUrl + "/all", Product[].class);
        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0, "Products list should not be empty");
    }
}
