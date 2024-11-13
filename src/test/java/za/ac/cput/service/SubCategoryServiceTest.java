package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.Application;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.factory.SubCategoryFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;


@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubCategoryServiceTest {
    @Autowired
    private SubCategoryService service;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    private SubCategory subCategory;
    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        category = categoryService.read(1L);
        product = productService.read(1L);

        SubCategory newSubCategory = SubCategoryFactory.createSubCategory(
                null,
                category,
                product
        );

        subCategory = service.create(newSubCategory);

    }

    @AfterEach
    void tearDown() {
        if (subCategory != null && subCategory.getId() != null && subCategory.getId() != 1 ) {
            service.delete(subCategory.getId());
        }
    }

    @Test
    void create() {
        SubCategory created = service.create(subCategory);
        assertEquals(subCategory.getId(), created.getId());
        System.out.println("Created: " + created);
    }

    @Test
    void read() {
        SubCategory read = service.read(subCategory.getId());
        assertNotNull(read);
        assertEquals(subCategory.getId(), read.getId());
        System.out.println("Read: " + read);
    }

    @Test
    void update() {
        SubCategory updated = new SubCategory.Builder()
                .copy(subCategory)
                .setCategory(category)
                .setProduct(product)
                .build();

        updated = service.update(updated);
        assertEquals(subCategory.getId(), updated.getId());
        System.out.println("Updated: " + updated);
    }

    @Test
    void delete() {
        boolean deleted = service.delete(16L);
        assertNull(service.read(16L));
        assertTrue(deleted);
        System.out.println("Deleted: " + service.read(subCategory.getId()));
    }

    @Test
    void findAll() {
        List<SubCategory> subCategories = service.findAll();
        assertFalse(subCategories.isEmpty());
        System.out.println("All SubCategories: " + subCategories);
    }

    @Test
    void findById() {
        SubCategory read = service.findById(subCategory.getId());
        assertNotNull(read);
        assertEquals(subCategory.getId(), read.getId());
        System.out.println("Read: " + read);
    }
}