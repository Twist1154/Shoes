package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
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
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    private List<Product> product;

    private SubCategory subCategory;


    @BeforeEach
    void setUp() {
        Product product1 = productService.read(30L);
        Product product2 = productService.read(31L);

        product = List.of(product1, product2);
        // Create and save a Category with bidirectional relationship
        Category category = CategoryFactory.createCategory(
                null,
                "Loafers",
                "this is Subcategory service test",
                LocalDateTime.now(),
                null
        );
        category = categoryService.create(category);

        subCategory = SubCategoryFactory.createSubCategory(
                null,
                category,
                product,
                "Low Tops",
                "this is subcategory service test",
                LocalDateTime.now(),
                null
        );
        subCategory = service.create(subCategory);

        // Add SubCategory to Category's list
      //  category.getSubCategories().add(subCategory);
        categoryService.update(category);
    }

    @AfterEach
    void tearDown() {
        if (subCategory != null && subCategory.getId() != null) {
            service.delete(subCategory.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(subCategory);
        assertNotNull(subCategory.getId());
        assertNotNull(subCategory.getCategory());
        //assertTrue(subCategory.getCategory().getSubCategories().contains(subCategory));
        System.out.println("Created: " + subCategory);
    }

    @Test
    @Order(2)
    void read() {
        SubCategory readSubCategory = service.read(subCategory.getId());
        assertNotNull(readSubCategory);
        assertEquals(subCategory.getId(), readSubCategory.getId());
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
    }

    @Test
    @Order(4)
    void findAll() {
        List<SubCategory> subCategories = service.findAll();
        assertNotNull(subCategories);
        assertFalse(subCategories.isEmpty());
    }

    @Test
    @Order(5)
    void findById() {
        SubCategory foundSubCategory = service.read(subCategory.getId());
        assertNotNull(foundSubCategory);
        assertEquals(subCategory.getId(), foundSubCategory.getId());
    }

    @Test
    @Order(6)
    void delete() {
        service.delete(subCategory.getId());
        SubCategory deletedSubCategory = service.read(subCategory.getId());
        assertNull(deletedSubCategory);

    }
}
