package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.service.SubCategoryService;

import java.util.List;

/**
 * SubCategoryController.java
 *
 * This class handles HTTP requests related to sub-categories.
 * It provides endpoints for CRUD operations on sub-category items.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    /**
     * Creates a new sub-category.
     *
     * @param subCategory the sub-category to be created
     * @return ResponseEntity containing the created SubCategory and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory createdSubCategory = subCategoryService.create(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubCategory);
    }

    /**
     * Retrieves a sub-category by its ID.
     *
     * @param id the ID of the sub-category to retrieve
     * @return ResponseEntity containing the SubCategory if found, or a 404 Not Found status if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) {
        SubCategory subCategory = subCategoryService.read(id);
        if (subCategory != null) {
            return ResponseEntity.ok(subCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing sub-category.
     *
     * @param id the ID of the sub-category to be updated
     * @param subCategory the updated sub-category details
     * @return ResponseEntity containing the updated SubCategory and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long id, @RequestBody SubCategory subCategory) {
        SubCategory updatedSubCategory = subCategoryService.update(subCategory);
        if (updatedSubCategory != null) {
            return ResponseEntity.ok(updatedSubCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a sub-category by its ID.
     *
     * @param id the ID of the sub-category to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        subCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all sub-categories.
     *
     * @return ResponseEntity containing the list of all SubCategories and HTTP status code
     */
    @GetMapping("/all")
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryService.findAll();
        return ResponseEntity.ok(subCategories);
    }


}
