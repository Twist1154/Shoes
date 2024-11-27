package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Category;
import za.ac.cput.repository.CategoryRepository;

import java.util.List;

/**
 * CategoryService.java
 * This service handles operations for managing Category entities.
 * It includes methods for creating, reading, updating, and deleting Categories.
 * */
@Slf4j
@Service
@Transactional
public class CategoryService implements ICategory {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false)
    public Category create(Category category) {
        return repository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category read(Long id) {
        return repository.findById(id).orElse(null);


    }

    @Override
    @Transactional(readOnly = false)
    public Category update(Category category) {
        if (category.getId() == null || !repository.existsById(category.getId())) {
            throw new IllegalArgumentException("Category with the given ID does not exist.");
        }

        Category updatedCategory = new Category.Builder()
                .setId(category.getId())  // Reusing the existing ID
                .setName(category.getName())
                .build();

        return repository.save(updatedCategory);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent Wishlist with ID: " + id);
            return false;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByName(String name) {
        return repository.findByName(name);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Category> findByNameContaining(String keyword) {
        return repository.findByNameContaining(keyword);
    }

}
