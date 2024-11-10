package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryFactoryTest {

    private Category category;

    @BeforeEach
    void setup() {
        // Set up a sample Category object using the factory method
        category = CategoryFactory.createCategory(
                1L,
                "LowTops"
        );
    }

    @Test
    void testCreateCategory() {
        // Verify that the Category object is not null
        assertNotNull(category);

        // Print the created Category object to the terminal
        System.out.println("Created Category: " + category);
    }

/*    @Test
    void testCreateCategory_WithNullId_ThrowsIllegalArgumentException() {
        // Try to create a Category object with a null ID
        assertThrows(IllegalArgumentException.class,
                () -> CategoryFactory.createCategory(
                        null,
                        "LowTops",
                        "description",
                        LocalDateTime.parse("2024-06-12T07:00:00"),
                        null));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating Category with null ID");
    }*/

    @Test
    void testCreateCategory_WithNullName_ThrowsIllegalArgumentException() {
        // Try to create a Category object with a null name
        assertThrows(IllegalArgumentException.class,
                () -> CategoryFactory.createCategory(
                        1L,
                        null
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating Category with null name");
    }

    @Test
    void testCreateCategory_WithNullDescription_ThrowsIllegalArgumentException() {
        // Try to create a Category object with a null description
        assertThrows(IllegalArgumentException.class,
                () -> CategoryFactory.createCategory(
                        1L,
                        "LowTops")
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating Category with null description");
    }
}