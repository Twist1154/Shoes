package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;
import za.ac.cput.enums.ProductAttributeType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductSkuFactoryTest {

    private List<SubCategory> subCategoryList;
    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        // Create a sample Category
        category = CategoryFactory.createCategory(
                1L,
                "LowTops"
        );

        product = new Product();

        // Create a sample SubCategory
        SubCategory subCategory = SubCategoryFactory.createSubCategory(
                1L,
                category,
                product);

        // Store the SubCategory in a List
        subCategoryList = Collections.singletonList(subCategory);  // or Arrays.asList if you have multiple subcategories
    }

    @Test
    void testCreateProductSku() {
        // Create sample ProductAttribute objects for size, color, and brand
        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10"
        );

        ProductAttribute colorAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.COLOR,
                "Green"
                );

        ProductAttribute brandAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.BRAND,
                "Nike"
        );

        // Create a sample Product object
        Product product = ProductFactory.createProduct(
                null,
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                null,
                subCategoryList,
                LocalDateTime.now()
        );

        // Create a sample ProductSkuService object
        ProductSku productSku = ProductSkuFactory.createProductSku(
                null,
                product,
                sizeAttribute,
                colorAttribute,
                brandAttribute,
                "SKU-123",
                100.0,
                10
        );

        // Verify that the ProductSkuService object is not null
        assertNotNull(productSku);

        // Print the created ProductSkuService object to the terminal
        System.out.println("Created ProductSkuService: " + productSku);
    }

    @Test
    void testCreateProductSku_WithNullProduct_ThrowsIllegalArgumentException() {
        // Create sample ProductAttribute objects for size, color, and brand
        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10");

        ProductAttribute colorAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.COLOR,
                "Green"
                );

        ProductAttribute brandAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.BRAND,
                "Nike"
        );

        // Try to create a ProductSkuService object with a null product
        assertThrows(IllegalArgumentException.class,
                () -> ProductSkuFactory.createProductSku(
                        null,
                        null,
                        sizeAttribute,
                        colorAttribute,
                        brandAttribute,
                        "SKU-123",
                        100.0,
                        10
                )
        );

        System.out.println("Expected IllegalArgumentException thrown when creating ProductSkuService with null product");
    }

    @Test
    void testCreateProductSku_WithNullSku_ThrowsIllegalArgumentException() {
        // Create sample ProductAttribute objects for size, color, and brand
        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10"
        );

        ProductAttribute colorAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.COLOR,
                "Green"
        );

        ProductAttribute brandAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.BRAND,
                "Nike"
        );

        // Create a sample Product object
        Product product = ProductFactory.createProduct(
                null,
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                null,
                subCategoryList,  // Passing the list of SubCategory
                LocalDateTime.now()
        );

        // Try to create a ProductSkuService object with a null SKU
        assertThrows(IllegalArgumentException.class,
                () -> ProductSkuFactory.createProductSku(
                        null,
                        product,
                        sizeAttribute,
                        colorAttribute,
                        brandAttribute,
                        null,
                        100.0,
                        10
                ));

        System.out.println("Expected IllegalArgumentException thrown when creating ProductSkuService with null SKU");
    }

    @Test
    void testCreateProductSku_WithZeroQuantity_ThrowsIllegalArgumentException() {
        // Create sample ProductAttribute objects for size, color, and brand
        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10"
        );

        ProductAttribute colorAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.COLOR,
                "Green"
        );

        ProductAttribute brandAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.BRAND,
                "Nike"
        );

        // Create a sample Product object
        Product product = ProductFactory.createProduct(
                null,
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                null,
                subCategoryList,  // Passing the list of SubCategory
                LocalDateTime.now()
        );

        // Try to create a ProductSkuService object with a zero quantity
        assertThrows(IllegalArgumentException.class,
                () -> ProductSkuFactory.createProductSku(
                        null,
                        product,
                        sizeAttribute,
                        colorAttribute,
                        brandAttribute,
                        "SKU-123",
                        100.0,
                        0
                )
        );

        System.out.println("Expected IllegalArgumentException thrown when creating ProductSkuService with zero quantity");
    }
}
