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
                null,
                "Low-Tops"
        );
    }

    @Test
    void testCreateCategory() {
        // Verify that the Category object is not null
        assertNotNull(category);
    }


    @Test
    void testCreateCategory_WithNullName_ThrowsIllegalArgumentException() {
        // Try to create a Category object with a null name
        assertThrows(IllegalArgumentException.class,
                () -> CategoryFactory.createCategory(
                        1L,
                        null
                )
        );

    }


}