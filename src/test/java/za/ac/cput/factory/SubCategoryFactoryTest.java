package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.SubCategory;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubCategoryFactoryTest {

    private SubCategory subCategory;
    private Category category;

    @BeforeEach
    void setup() {
        // Set up a sample Category object
        category = new Category();

        // Set up a sample SubCategory object using the factory method
        subCategory = SubCategoryFactory.createSubCategory(
                1L,
                category,
                "Sneakers",
                "Sneakers",
                LocalDateTime.parse("2024-06-12T07:00:00"),
                null);
    }

    @Test
    void testCreateSubCategory() {
        // Verify that the SubCategory object is not null
        assertNotNull(subCategory);

        // Print the created SubCategory object to the terminal
        System.out.println("Created SubCategory: " + subCategory);
    }

    @Test
    void testCreateSubCategory_WithNullName_ThrowsIllegalArgumentException() {
        // Try to create a SubCategory object with null name
        assertThrows(IllegalArgumentException.class,
                () -> SubCategoryFactory.createSubCategory(
                        1L,
                        category,
                        null,
                        "Electronics sub-category",
                        LocalDateTime.parse("2024-06-12T07:00:00"),
                        null));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating SubCategory with null name");
    }

    @Test
    void testCreateSubCategory_WithNullDescription_ThrowsIllegalArgumentException() {
        // Try to create a SubCategory object with null description
        assertThrows(IllegalArgumentException.class,
                () -> SubCategoryFactory.createSubCategory(
                        1L,
                        category,
                        "Electronics",
                        null,
                        LocalDateTime.parse("2024-06-12T07:00:00"),
                        null));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating SubCategory with null description");
    }
}