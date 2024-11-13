package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.service.CategoryService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/api/categories";

    @Autowired
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = CategoryFactory.createCategory(
                null,
                "Sports"
        );
        ResponseEntity<Category> response = restTemplate.postForEntity(baseUrl, category, Category.class);
        category = response.getBody();
        assertNotNull(category);
        assertNotNull(category.getId());
    }

    @AfterEach
    void tearDown() {
        if (category != null && category.getId() != null) {
            restTemplate.delete(baseUrl + "/" + category.getId());
        }
    }

    @Test
    @Order(1)
    void createCategory() {
        Category newCategory = CategoryFactory.createCategory(null, "Electronics");
        ResponseEntity<Category> response = restTemplate.postForEntity(baseUrl, newCategory, Category.class);
        Category createdCategory = response.getBody();
        assertNotNull(createdCategory);
        assertNotNull(createdCategory.getId());
        assertEquals("Electronics", createdCategory.getName());
    }

    @Test
    @Order(2)
    void getCategoryById() {
        ResponseEntity<Category> response = restTemplate.getForEntity(baseUrl + "/" + category.getId(), Category.class);
        assertEquals(category.getId(), response.getBody().getId());
    }

    @Test
    @Order(3)
    void updateCategory() {
        Category updatedCategory = new Category.Builder()
                .copy(category)
                .setName("Updated Sports")
                .build();
        restTemplate.put(baseUrl + "/" + category.getId(), updatedCategory);

        ResponseEntity<Category> response = restTemplate.getForEntity(baseUrl + "/" + category.getId(), Category.class);
        assertEquals("Updated Sports", response.getBody().getName());
    }

    @Test
    @Order(4)
    void deleteCategory() {
        restTemplate.delete(baseUrl + "/" + category.getId());
        ResponseEntity<Category> response = restTemplate.getForEntity(baseUrl + "/" + category.getId(), Category.class);
        assertEquals(404, response.getStatusCodeValue()); // Assuming 404 for not found
    }

    @Test
    @Order(5)
    void getAllCategories() {
        ResponseEntity<Category[]> response = restTemplate.getForEntity(baseUrl, Category[].class);
        List<Category> categories = List.of(response.getBody());
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    @Order(6)
    void findByName() {
        String name = "Sports";
        ResponseEntity<Category[]> response = restTemplate.getForEntity(baseUrl + "/name/" + name, Category[].class);
        List<Category> categories = List.of(response.getBody());
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.stream().anyMatch(cat -> cat.getName().equals(name)));
    }

    @Test
    @Order(7)
    void findByNameContaining() {
        String partialName = "Spo";
        ResponseEntity<Category[]> response = restTemplate.getForEntity(baseUrl + "/name-contains/" + partialName, Category[].class);
        List<Category> categories = List.of(response.getBody());
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.stream().anyMatch(cat -> cat.getName().contains(partialName)));
    }
}
