package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.SubCategory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubCategoryFactoryTest {

    private SubCategory subCategory;
    private Category category;
    private Product product;

    @BeforeEach
    void setup() {
        // Set up a sample Category object
        category = new Category();
        product = new Product();
        // Set up a sample SubCategory object using the factory method
        subCategory = SubCategoryFactory.createSubCategory(
                1L,
                category,
                product);
    }

    @Test
    void testCreateSubCategory() {
        // Verify that the SubCategory object is not null
        assertNotNull(subCategory);
    }


}