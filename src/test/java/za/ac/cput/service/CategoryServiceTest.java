package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
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
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    private Category category;
    private Category updatedCategory;

    @BeforeEach
    void setUp() {
        category = CategoryFactory.createCategory(
                null,
                "Art"
        );

        category = categoryService.create(category);
    }

    @AfterEach
    void tearDown() {
       // categoryService.delete(category.getId());
    }

    @Test
    @Order(1)
    void create() {
        Category createdCategory = categoryService.create(CategoryFactory.createCategory(
                null,
                "Craft"
        )
        );

        assertNotNull(createdCategory);
        assertEquals("Craft", createdCategory.getName());
        categoryService.delete(createdCategory.getId());
    }

    @Test
    @Order(2)
    void read() {
        Category foundCategory = categoryService.read(category.getId());
        System.out.println(foundCategory);
        assertNotNull(foundCategory);
        assertEquals("Art", foundCategory.getName());
    }

    @Test
    @Order(3)
    void update() {
        updatedCategory = new Category.Builder()
                .copy(category)
                .setName("Updated Art")
                .build();
        Category result = categoryService.update(updatedCategory);
        assertNotNull(result);
        assertEquals("Updated Art", result.getName());
    }

    @Test
    @Order(4)
    void delete() {
        categoryService.delete(category.getId());
        Category deletedCategory = categoryService.read(category.getId());
        System.out.println(deletedCategory);
        assertNull(deletedCategory);
    }

    @Test
    @Order(5)
    void findAll() {
        List<Category> categories = categoryService.findAll();
        assertFalse(categories.isEmpty());
    }

    @Test
    @Order(6)
    void findByName() {
        List<Category> categories = categoryService.findByName("Art");
        assertFalse(categories.isEmpty());
    }



    @Test
    @Order(7)
    void findByNameContaining() {
        List<Category> categories = categoryService.findByNameContaining("Art");
        assertFalse(categories.isEmpty());
    }


}
