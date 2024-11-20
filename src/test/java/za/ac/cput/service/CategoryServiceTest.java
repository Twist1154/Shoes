package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.Application;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    private Category category;
    private Category updatedCategory;

    @BeforeEach
    void setUp() {
        category = CategoryFactory.createCategory(
                null,
                "Sports"
        );

        category = categoryService.create(category);
    }

    @AfterEach
    void tearDown() {
        if (category.getId() != null && category.getId() > 2) {
            categoryService.delete(category.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(category);
        System.out.println("Created category: \n" + category);
        assertEquals("Sports", category.getName());
    }

    @Test
    @Order(2)
    void read() {
        Category foundCategory = categoryService.read(category.getId());
        System.out.println("Found category: \n" + foundCategory);
        assertNotNull(foundCategory);
        assertEquals("Sports", foundCategory.getName());
    }

    @Test
    @Order(3)
    void update() {
        updatedCategory = new Category.Builder()
                .copy(category)
                .setName("Low dunks")
                .build();
        Category result = categoryService.update(updatedCategory);
        System.out.println("Updated category: \n" + result);
        assertNotNull(result);
        assertEquals("Low dunks", result.getName());
    }

    @Test
    @Order(4)
    void delete() {
        boolean deleted = categoryService.delete(category.getId());
        Category deletedCategory = categoryService.read(category.getId());
        System.out.println("should be deleted: \n"+deletedCategory);
        assertTrue(deleted);
        assertNull(deletedCategory);
    }

    @Test
    @Order(5)
    void findAll() {
        List<Category> categories = categoryService.findAll();
        System.out.println("All categories: \n" + categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    @Order(6)
    void findByName() {
        List<Category> categories = categoryService.findByName("Sports");
        System.out.println("Categories found by name: \n" + categories);
        assertFalse(categories.isEmpty());
    }



    @Test
    @Order(7)
    void findByNameContaining() {
        List<Category> categories = categoryService.findByNameContaining("sports");
        System.out.println("Categories found by name containing: \n" + categories);
        assertFalse(categories.isEmpty());
    }


}
