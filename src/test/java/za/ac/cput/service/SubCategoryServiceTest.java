package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.SubCategoryFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SubCategoryServiceTest {

    @Autowired
    private SubCategoryService service;

    @Autowired
    private CategoryService categoryService; // Assuming you have a CategoryService to manage categories

    private SubCategory subCategory;

    @BeforeEach
    void setUp() {
        // Create and save a Category first
        Category category = CategoryFactory.createCategory(
                null,
                "Loafers",
                "this is Subcategory service test",
                LocalDateTime.now(),
                null
        );
        category = categoryService.create(category); // Save category to DB

        // Now create the SubCategory
        subCategory = SubCategoryFactory.createSubCategory(
                null,
                category,
                "Low Tops",
                "this is subcategory service test",
                LocalDateTime.now(),
                null
        );
        subCategory = service.create(subCategory); // Save subcategory
    }

    @AfterEach
    void tearDown() {
       /* if (subCategory != null && subCategory.getId() != null) {
            service.delete(subCategory.getId());
        }*/
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(subCategory);
        assertNotNull(subCategory.getId());
        System.out.println("Created: " + subCategory);
    }

    @Test
    @Order(2)
    void read() {
        SubCategory readSubCategory = service.read(subCategory.getId());
        assertNotNull(readSubCategory);
        assertEquals(subCategory.getId(), readSubCategory.getId());
        System.out.println("Read: " + readSubCategory);
    }

    @Test
    @Order(3)
    void update() {
        subCategory = new SubCategory.Builder()
                .copy(subCategory)
                .setName("Updated Low Tops")
                .build();
        SubCategory updatedSubCategory = service.update(subCategory);
        assertNotNull(updatedSubCategory);
        assertEquals("Updated Low Tops", updatedSubCategory.getName());
        System.out.println("Updated: " + updatedSubCategory);
    }

    @Test
    @Order(4)
    void findAll() {
        List<SubCategory> subCategories = service.findAll();
        assertNotNull(subCategories);
        assertFalse(subCategories.isEmpty());
        System.out.println("Found all subcategories: " + subCategories);
    }

    @Test
    @Order(5)
    void findById() {
        SubCategory foundSubCategory = service.findById(subCategory.getId());
        assertNotNull(foundSubCategory);
        assertEquals(subCategory.getId(), foundSubCategory.getId());
        System.out.println("Found by ID: " + foundSubCategory);
    }

    @Test
    @Order(6)
    void delete() {
        service.delete(subCategory.getId());
        SubCategory deletedSubCategory = service.read(subCategory.getId());
        assertNull(deletedSubCategory);
        System.out.println("Deleted subcategory with ID: " + subCategory.getId());
    }
}