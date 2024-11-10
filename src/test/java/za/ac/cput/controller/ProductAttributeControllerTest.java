package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.factory.ProductAttributeFactory;
import za.ac.cput.service.ProductAttributeService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductAttributeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductAttributeService productAttributeService;

    private ProductAttribute productAttribute;

    private final String baseUrl = "http://localhost:8080/store/product-attributes";

    @BeforeEach
    void setUp() {
        ProductAttribute color = productAttributeService.read(136L);
        ProductAttribute size = productAttributeService.read(137L);
        ProductAttribute brand = productAttributeService.read(138L);
        // Create a new ProductAttribute for testing
        productAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10"
        );
    }

    @AfterEach
    void tearDown() {
        /*if (productAttribute != null && productAttribute.getId() != null) {
            productAttributeService.delete(productAttribute.getId());
        }*/
    }

    @Test
    @Order(1)
    void createProductAttribute() {
        // Create a new ProductAttribute via the REST API
        ResponseEntity<ProductAttribute> response = restTemplate.postForEntity(
                baseUrl +
                        "/create",
                productAttribute,
                ProductAttribute.class
        );

        System.out.println(response.getBody());

        assertNotNull(response.getBody(), "Created ProductAttribute should not be null");
        productAttribute = response.getBody();  // Store the created ProductAttribute for future tests
        assertNotNull(productAttribute.getId(), "ProductAttribute ID should not be null after creation");
    }

    @Test
    @Order(2)
    void getProductAttributeById() {
        System.out.println("ProductAttribute that must be returned: " + "\n " + productAttribute);
        assertNotNull(productAttribute, "ProductAttribute must be created before testing get by ID");

        // Fetch the ProductAttribute by ID
        ResponseEntity<ProductAttribute> response = restTemplate.getForEntity(
                baseUrl +
                        "/read/" +
                        productAttribute.getId(),
                ProductAttribute.class
        );
        System.out.println("ProductAttribute updated: " + "\n " + response.getBody());
        assertNotNull(response.getBody(), "Fetched ProductAttribute should not be null");
        assertEquals(productAttribute.getId(), response.getBody().getId(), "ProductAttribute ID should match");
    }

    @Test
    @Order(3)
    void updateProductAttribute() {
        // Fetch the existing ProductAttribute before updating
        ResponseEntity<ProductAttribute> existingResponse = restTemplate.getForEntity(baseUrl + "/read/" + productAttribute.getId(), ProductAttribute.class);
        ProductAttribute existingProductAttribute = existingResponse.getBody();

        assertNotNull(existingProductAttribute, "Existing ProductAttribute should not be null before update");

        // Modify the ProductAttribute details
    ProductAttribute updatedProductAttribute = new ProductAttribute.Builder()
                .copy(existingProductAttribute)
                .setValue("Updated Size")
                .build();

    HttpEntity<ProductAttribute> requestUpdate = new HttpEntity<>(updatedProductAttribute);

        // Send the PUT request to update the ProductAttribute
        ResponseEntity<ProductAttribute> response = restTemplate.exchange(
            baseUrl + "/update/" + existingProductAttribute.getId(), // Use existing attribute ID
                HttpMethod.PUT,
                requestUpdate,
                ProductAttribute.class
        );

        assertNotNull(response.getBody(), "Updated ProductAttribute should not be null");
        assertEquals("Updated Size", response.getBody().getValue(), "ProductAttribute value should be updated");
    }


    @Test
    @Order(4)
    void deleteProductAttribute() {
        // Send DELETE request to remove the ProductAttribute
        restTemplate.delete(baseUrl + "/" + productAttribute.getId());

        // Attempt to read the deleted ProductAttribute to ensure it's gone
        ResponseEntity<ProductAttribute> response = restTemplate.getForEntity(baseUrl + "/read/" + productAttribute.getId(), ProductAttribute.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Deleted ProductAttribute should not be found");
    }

    @Test
    @Order(5)
    void getAllProductAttributes() {
        // Fetch all ProductAttributes
        ResponseEntity<ProductAttribute[]> response = restTemplate.getForEntity(baseUrl + "/all", ProductAttribute[].class);

        assertNotNull(response.getBody(), "ProductAttributes list should not be null");
        assertTrue(response.getBody().length > 0, "ProductAttributes list should not be empty");

        // Log the ProductAttributes list
        System.out.println("Fetched ProductAttributes: " + List.of(response.getBody()));
    }
}
