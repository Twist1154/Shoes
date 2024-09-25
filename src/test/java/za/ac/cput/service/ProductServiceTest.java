package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.ImageUrlsFactory;
import za.ac.cput.factory.ProductFactory;
import za.ac.cput.factory.SubCategoryFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;

    private Product product;

    @BeforeEach
    void setUp() {
        // Create Category and SubCategory
        Category category = CategoryFactory.createCategory(
                null, // ID should be generated
                "Sneakers",
                "Sneakers",
                LocalDateTime.now(),
                null
        );
        category = categoryService.create(category);

        SubCategory subCategory = SubCategoryFactory.createSubCategory(
                null, // ID should be generated
                category,
                "High Tops",
                "High Top Sneakers",
                LocalDateTime.now(),
                null
        );
        subCategory = subCategoryService.create(subCategory);

        // Set up the Product
        ImageUrls imageUrls = ImageUrlsFactory.createImageUrls(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg"
        );

        product = ProductFactory.createProduct(
                null, // ID should be generated
                "AirForce 1",
                "All White AirForce 1",
                "Nike AirForce 1",
                "cover img url",
                imageUrls,
                List.of(subCategory),
                LocalDateTime.now(),
                null
        );
    }

    @AfterEach
    void tearDown() {
        // Check if the product exists before deleting to avoid exceptions
        /*if (product != null && product.getId() != null) {
            productService.delete(product.getId());
        }*/
    }

    @Test
    @Order(1)
    void create() {
        Product createdProduct = productService.create(product);
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId()); // Ensure ID was generated
        assertEquals(product.getName(), createdProduct.getName());
    }

    @Test
    @Order(2)
    void read() {
        Product createdProduct = productService.create(product);
        Product readProduct = productService.read(createdProduct.getId());
        assertNotNull(readProduct);
        assertEquals(createdProduct.getId(), readProduct.getId());
    }

    @Test
    @Order(3)
    void update() {
        Product createdProduct = productService.create(product);
        Product updatedProduct = new Product.Builder()
                .copy(createdProduct)
                .setName("Updated Product")
                .build();
        Product updated = productService.update(updatedProduct);
        assertNotNull(updated);
        assertEquals("Updated Product", updated.getName());
    }

    @Test
    @Order(4)
    void delete() {
        Product createdProduct = productService.create(product);
        productService.delete(createdProduct.getId());
        Product deletedProduct = productService.read(createdProduct.getId());
        assertNull(deletedProduct);  // Ensure product was deleted, should return null
    }

    @Test
    @Order(5)
    void findAll() {
        productService.create(product);  // Ensure Product is created for testing
        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);  // Ensure at least one product exists
    }
}