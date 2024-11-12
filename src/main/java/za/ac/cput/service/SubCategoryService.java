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
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class SubCategoryService implements ISubCategory {

    private final SubCategoryRepository repository;

    @Autowired
    public SubCategoryService(SubCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public SubCategory create(SubCategory subCategory) {
        return repository.save(subCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategory read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public SubCategory update(SubCategory subCategory) {
        SubCategory existingSubCategory = repository.findById(subCategory.getId()).orElse(null);
        if (existingSubCategory != null) {
            SubCategory updatedSubCategory = new SubCategory.Builder()
                    .copy(existingSubCategory)
                    .setId(existingSubCategory.getId())
                    .setProduct(subCategory.getProduct())
                    .setCategory(subCategory.getCategory())
                    .build();
            return repository.save(updatedSubCategory);
        } else {
            return null;
        }
    }
@Transactional(readOnly = false)
    public boolean delete(Long id) {
    if (repository.existsById(id)) {
        repository.deleteById(id);
        return true;
    }
    log.warn("Attempt to delete a non-existent sub-category with ID: {}", id);
    return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategory> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategory findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}