package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.ImageUrls;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductFactoryTest {
    private Product product;
    private Category category;
    private List<SubCategory> subCategory;
    private ImageUrls imageUrls;

    @Test
    void testCreateProduct() {

        // Create a sample ImageUrls object using the factory method
        imageUrls = ImageUrlsFactory.createImageUrls(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg"
        );

        // Create a sample Product object using the factory method
        product = ProductFactory.createProduct(
                1L,
                "Product Name",
                "Product Description",
                "Product Summary",
                "Product Cover",
                imageUrls,
                LocalDateTime.now()
        );

        // Verify that the Product object is not null
        assertNotNull(product);

        // Print the created Product object to the terminal
        System.out.println("Created Product: " + product);
    }

    @Test
    void testCreateProduct_WithNullName_ThrowsIllegalArgumentException() {
        // Try to create a Product object with a null name
        assertThrows(IllegalArgumentException.class, () -> ProductFactory.createProduct(
                1L,
                null,
                "Product Description",
                "Product Summary",
                "Product Cover",
                imageUrls,
                LocalDateTime.now()
        ));

        System.out.println("Expected IllegalArgumentException thrown when creating Product with null name");
        System.out.println("Created Product with null name: " + product);    }

    @Test
    void testCreateProduct_WithNullDescription_ThrowsIllegalArgumentException() {
        // Try to create a Product object with a null description
        assertThrows(IllegalArgumentException.class, () -> ProductFactory.createProduct(
                1L,
                "Product Name",
                null,
                "Product Summary",
                "Product Cover",
                imageUrls,
                LocalDateTime.now()
        ));

        System.out.println("Expected IllegalArgumentException thrown when creating Product with null description");
        System.out.println("Created Product with null Description: " + product);
    }

    @Test
    void testCreateProduct_WithNullSummary_ThrowsIllegalArgumentException() {
        // Try to create a Product object with a null summary
        assertThrows(IllegalArgumentException.class, () -> ProductFactory.createProduct(
                1L,
                "Product Name",
                "Product Description",
                null,
                "Product Cover",
                imageUrls,
                LocalDateTime.now()
                )
        );

        System.out.println("Expected IllegalArgumentException thrown when creating Product with null summary");
        System.out.println("Created Product with null Summary: " + product);
    }

    @Test
    void testCreateProduct_WithNullCover_ThrowsIllegalArgumentException() {
        // Try to create a Product object with a null cover
        assertThrows(IllegalArgumentException.class, () -> ProductFactory.createProduct(
                1L,
                "Product Name",
                "Product Description",
                "Product Summary",
                null,
                imageUrls,
                LocalDateTime.now()));

        System.out.println("Expected IllegalArgumentException thrown when creating Product with null cover");
        System.out.println("Created Product with null Cover: " + product);
    }

}
