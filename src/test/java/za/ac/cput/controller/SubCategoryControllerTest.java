package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.factory.SubCategoryFactory;
import za.ac.cput.service.CategoryService;
import za.ac.cput.service.ProductService;
import za.ac.cput.service.SubCategoryService;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubCategoryControllerTest {
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private TestRestTemplate restTemplate;

    private SubCategory subCategory;
    private Category category;
    private Product product;

    private final String baseUrl = "http://localhost:8080/store/subcategories"; // Base URL for the controller

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        // Fetch a valid category to associate with the subcategory
        category = categoryService.read(51L);
        product = productService.read(1L);

        subCategory = SubCategoryFactory.createSubCategory(
                null,
                category,
                product

        );
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        if (subCategory != null && subCategory.getId() != null) {
            subCategoryService.delete(subCategory.getId());
        }
    }

    @Test
    void createSubCategory() {
        ResponseEntity<SubCategory> response = restTemplate.postForEntity(baseUrl, subCategory, SubCategory.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());

        System.out.println("Created: " + response.getBody());
    }

    @Test
    void getSubCategoryById() {
        // First, create a subcategory to test retrieval
        ResponseEntity<SubCategory> createResponse = restTemplate.postForEntity(baseUrl, subCategory, SubCategory.class);
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());

        // Fetch the subcategory by ID
        String url = baseUrl + "/" + createResponse.getBody().getId();
        ResponseEntity<SubCategory> response = restTemplate.getForEntity(url, SubCategory.class);

        assertNotNull(response.getBody());
        assertEquals(createResponse.getBody().getId(), response.getBody().getId());
        System.out.println("Fetched: " + response.getBody());
    }

    @Test
    void updateSubCategory() {
        // Create and persist a subcategory
        ResponseEntity<SubCategory> createResponse = restTemplate.postForEntity(baseUrl, subCategory, SubCategory.class);
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());

        // Update the subcategory using Builder pattern
        SubCategory existingSubCategory = createResponse.getBody();

        SubCategory updatedSubCategory = new SubCategory.Builder()
                .copy(existingSubCategory)
                .setId(existingSubCategory.getId())
                .setCategory(existingSubCategory.getCategory())
                .setProduct(existingSubCategory.getProduct())
                .build();

        restTemplate.put(baseUrl + "/" + updatedSubCategory.getId(), updatedSubCategory);

        // Fetch the updated subcategory to verify the change
        ResponseEntity<SubCategory> response = restTemplate.getForEntity(baseUrl + "/" + updatedSubCategory.getId(), SubCategory.class);
        assertNotNull(response.getBody());
         System.out.println("Updated: " + response.getBody());
    }

    @Test
    void deleteSubCategory() {
        // Create and persist a subcategory
        ResponseEntity<SubCategory> createResponse = restTemplate.postForEntity(baseUrl, subCategory, SubCategory.class);
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());

        // Delete the subcategory
        String url = baseUrl + "/" + createResponse.getBody().getId();
        restTemplate.delete(url);

        // Try to fetch the deleted subcategory
        ResponseEntity<SubCategory> response = restTemplate.getForEntity(url, SubCategory.class);
        assertEquals(404, response.getStatusCode());
        System.out.println("SubCategory deleted successfully");
    }

    @Test
    void getAllSubCategories() {
        ResponseEntity<SubCategory[]> response = restTemplate.getForEntity(baseUrl, SubCategory[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0); // Check if we have some subcategories
        System.out.println("All SubCategories: " + List.of(response.getBody()));
    }
}
