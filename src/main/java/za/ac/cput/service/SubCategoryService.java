package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.repository.SubCategoryRepository;

import java.util.List;

/**
 * SubCategoryService.java
 *
 * Author: Rethabile Ntsekhe
 * Student Num: 220455430
 * Date: 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class SubCategoryService implements ISubCategory {

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategory create(SubCategory subCategory) {
        log.info("Creating a new SubCategory: {}", subCategory);
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategory read(Long id) {
        log.info("Reading SubCategory with ID: {}", id);
        return subCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public SubCategory update(SubCategory subCategory) {
        log.info("Updating SubCategory: {}", subCategory);
        SubCategory existingSubCategory = subCategoryRepository.findById(subCategory.getId()).orElse(null);
        if (existingSubCategory != null) {
            SubCategory updatedSubCategory = new SubCategory.Builder()
                    .copy(existingSubCategory)
                    .setCategory(subCategory.getCategory())
                    .setName(subCategory.getName())
                    .setDescription(subCategory.getDescription())
                    .setCreatedAt(existingSubCategory.getCreatedAt())
                    .setDeletedAt(existingSubCategory.getDeletedAt())
                    .build();
            return subCategoryRepository.save(updatedSubCategory);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting SubCategory with ID: {}", id);
        subCategoryRepository.deleteById(id);
        return !subCategoryRepository.existsById(id);
    }

    @Override
    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    @Override
    public List<SubCategory> findSubCategoriesByCategory_Id(Long categoryId) {
        return subCategoryRepository.findSubCategoriesByCategory_Id(categoryId);
    }

    @Override
    public List<SubCategory> findSubCategoriesByProduct_Id(Long productId) {
        return subCategoryRepository.findSubCategoriesByProduct_Id(productId);
    }
}
