package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.SubCategory;
import za.ac.cput.repository.SubCategoryRepository;

import java.util.List;

/**
 * SubCategoryService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
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
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategory read(Long id) {
        return subCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public SubCategory update(SubCategory subCategory) {
        SubCategory existingSubCategory = subCategoryRepository.findById(subCategory.getId()).orElse(null);
        if (existingSubCategory != null) {
            SubCategory updatedSubCategory = new SubCategory.Builder()
                    .copy(existingSubCategory)
                    .setId(existingSubCategory.getId())
                    .setCategory(subCategory.getCategory())
                    .setName(subCategory.getName())
                    .setDescription(subCategory.getDescription())
                    .setCreatedAt(existingSubCategory.getCreatedAt())
                    .setDeletedAt(existingSubCategory.getDeletedAt())
                    .build();
            return subCategoryRepository.save(updatedSubCategory);
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        subCategoryRepository.deleteById(id);

        // Check if the entity still exists after deletion
        boolean exists = subCategoryRepository.existsById(id);

        // Return false if entity was deleted successfully, otherwise return true
        return !exists;
    }

    @Override
    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    @Override
    public SubCategory findById(Long id) {
        return subCategoryRepository.findById(id).orElse(null);
    }
}