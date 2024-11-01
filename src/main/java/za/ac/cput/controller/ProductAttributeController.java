package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.domain.ProductSku;
import za.ac.cput.service.ProductAttributeService;

import java.util.List;

/**
 * ProductAttributeController.java
 *
 * This class handles HTTP requests related to product attributes.
 * It provides endpoints for CRUD operations on product attribute items.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/product-attributes")
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @Autowired
    public ProductAttributeController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    /**
     * Creates a new product attribute.
     *
     * @param productAttribute the product attribute to be created
     * @return ResponseEntity containing the created ProductAttribute and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<ProductAttribute> createProductAttribute(@RequestBody ProductAttribute productAttribute) {
        ProductAttribute createdProductAttribute = productAttributeService.create(productAttribute);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductAttribute);
    }

    /**
     * Retrieves a product attribute by its ID.
     *
     * @param id the ID of the product attribute to retrieve
     * @return ResponseEntity containing the ProductAttribute if found, or a 404 Not Found status if not
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<ProductAttribute> getProductAttributeById(@PathVariable Long id) {
        ProductAttribute productAttribute = productAttributeService.read(id);
        if (productAttribute != null) {
            return ResponseEntity.ok(productAttribute);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing product attribute.
     *
     * @param id the ID of the product attribute to be updated
     * @param productAttribute the updated product attribute details
     * @return ResponseEntity containing the updated ProductAttribute and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductAttribute> updateProductAttribute(@PathVariable Long id, @RequestBody ProductAttribute productAttribute) {
       // productAttribute.setId(id); // Set the ID from the path
        ProductAttribute updatedProductAttribute= productAttributeService.update(productAttribute);
        if (updatedProductAttribute != null) {
            return ResponseEntity.ok(updatedProductAttribute);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a product attribute by its ID.
     *
     * @param id the ID of the product attribute to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductAttribute(@PathVariable Long id) {
        ProductAttribute productAttribute = productAttributeService.read(id);
        if (productAttribute != null) {
            productAttributeService.delete(id);
            return ResponseEntity.noContent().build(); // Return 204 if deletion is successful
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if the attribute does not exist
        }
    }

    /**
     * Retrieves all product attributes.
     *
     * @return ResponseEntity containing the list of all ProductAttributes and HTTP status code
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductAttribute>> getAllProductAttributes() {
        List<ProductAttribute> productAttributes = productAttributeService.findAll();
        return ResponseEntity.ok(productAttributes);
    }
}
