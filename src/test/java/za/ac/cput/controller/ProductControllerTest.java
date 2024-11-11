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
import za.ac.cput.domain.Category;
import za.ac.cput.factory.ImageUrlsFactory;
import za.ac.cput.factory.ProductFactory;
import za.ac.cput.service.ProductService;
import za.ac.cput.service.SubCategoryService;
import za.ac.cput.service.CategoryService;

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

    @Autowired
    private CategoryService categoryService;

    private Product product;
    private SubCategory subCategory;
    private Category category;
    private ImageUrls image;

    private final String baseUrl = "http://localhost:8080/store/products";

    @BeforeEach
    void setUp() {
        // Create image URLs for the product
        image = ImageUrlsFactory.createImageUrls(
                "null",
                "cover image url",
                "thumbnail image url",
                "null"
        );

        // Create a new product linked to the subcategory
        product = ProductFactory.createProduct(
                null,
                "Product Controller Test",
                "Product Controller create test",
                "Product Controller summary",
                "cover image url",
                image,
                LocalDateTime.now()
        );
    }

    @AfterEach
    void tearDown() {
        // Clean up created entities after each test
        if (product != null && product.getId() != null) {
            productService.delete(product.getId());
        }

        if (subCategory != null && subCategory.getId() != null) {
            subCategoryService.delete(subCategory.getId());
        }

        if (category != null && category.getId() != null) {
            categoryService.delete(category.getId());
        }
    }

    @Test
    @Order(1)
    void createProduct() {
        // Create a new Product via the REST API
        ResponseEntity<Product> response = restTemplate.postForEntity(baseUrl + "/create", product, Product.class);
        System.out.println(response.getBody());
        System.out.println("created product above:  ------------------------------------");
        assertNotNull(response.getBody(), "Created product should not be null");
        product = response.getBody();  // Store the created product for future tests
        assertNotNull(product.getId(), "Product ID should not be null after creation");
    }

    @Test
    @Order(2)
    void getProductById() {
        assertNotNull(product, "Product must be created before testing get by ID");

        // Fetch the Product by ID
        ResponseEntity<Product> response = restTemplate.getForEntity(baseUrl + "/read/" + product.getId(), Product.class);

        assertNotNull(response.getBody(), "Fetched product should not be null");
        assertEquals(product.getId(), response.getBody().getId(), "Product ID should match");
    }

    @Test
    @Order(3)
    void updateProduct() {
        // Fetch the existing product before updating
        ResponseEntity<Product> existingResponse = restTemplate.getForEntity(baseUrl + "/read/" + product.getId(), Product.class);
        Product existingProduct = existingResponse.getBody();

        assertNotNull(existingProduct, "Existing product should not be null before update");
        System.out.println("Fetched existing product: " + existingProduct);

        // Modify the product details
        product = new Product.Builder()
                .copy(existingProduct)
                .setName("Updated Product Name")
                .build();

        System.out.println("Modified product: " + product);
        HttpEntity<Product> requestUpdate = new HttpEntity<>(product);

        // Send the PUT request to update the product
        ResponseEntity<Product> response = restTemplate.exchange(
                baseUrl + "/update/" + product.getId(),
                HttpMethod.PUT,
                requestUpdate,
                Product.class
        );

        System.out.println("Sent this to be updated: " + response.getBody());
        assertNotNull(response.getBody(), "Updated product should not be null");
        assertEquals("Updated Product Name", response.getBody().getName(), "Product name should be updated");
    }

    @Test
    @Order(4)
    void deleteProduct() {
        // Log the product to be deleted
        System.out.println("Product to be deleted: " + product);

        // Fetch the existing product before deletion
        ResponseEntity<Product> existingResponse = restTemplate.getForEntity(baseUrl + "/read/" + product.getId(), Product.class);
        assertNotNull(existingResponse.getBody(), "Product must exist before deletion");

        // Delete the product by ID
        restTemplate.delete(baseUrl + "/delete/" + product.getId());

        // Check if the product was deleted
        ResponseEntity<Product> deletedProduct = restTemplate.getForEntity(baseUrl + "/read/" + product.getId(), Product.class);
        assertNull(deletedProduct.getBody(), "Deleted product should not be found");

        System.out.println("Product deleted successfully");
    }

    @Test
    @Order(5)
    void getAllProducts() {
        // Fetch all products
        ResponseEntity<Product[]> response = restTemplate.getForEntity(baseUrl + "/all", Product[].class);

        assertNotNull(response.getBody(), "Products list should not be null");
        assertTrue(response.getBody().length > 0, "Products list should not be empty");

        // Log the products list
        System.out.println("Fetched products: " + List.of(response.getBody()));
    }
}
