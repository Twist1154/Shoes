package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.ProductSubCategories;
import za.ac.cput.repository.ProductSubCategoriesRepository;

import java.util.List;

/**
 * ProductSubCategoryService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Sep-24
 */
@Service
public class ProductSubCategoryService implements IProductSubCategories {

    private final ProductSubCategoriesRepository subCategoriesRepository;

    @Autowired
    public ProductSubCategoryService(ProductSubCategoriesRepository subCategoriesRepository) {
        this.subCategoriesRepository = subCategoriesRepository;
    }

    @Override
    public ProductSubCategories findById(Long id) {
        return subCategoriesRepository.findById(id).orElse(null);
    }

    @Override
    public ProductSubCategories create(ProductSubCategories productSubCategories) {
        return subCategoriesRepository.save(productSubCategories);
    }

    @Override
    public ProductSubCategories read(Long aLong) {
        return subCategoriesRepository.findById(aLong).orElse(null);
    }

    @Override
    public ProductSubCategories update(ProductSubCategories productSubCategories) {
        ProductSubCategories existingProductSubCategories = subCategoriesRepository.findById(productSubCategories.getId()).orElse(null);
        if (existingProductSubCategories != null) {
            // Update the mutable fields directly
            existingProductSubCategories.setProduct(productSubCategories.getProduct());
            existingProductSubCategories.setSubCategory(productSubCategories.getSubCategory());
            return subCategoriesRepository.save(existingProductSubCategories);
        }
        return null;
    }

    @Override
    public List<ProductSubCategories> findAll() {
        return subCategoriesRepository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        ProductSubCategories subCategory = subCategoriesRepository.findById(id).orElse(null);
        if (subCategory != null) {
            // Remove the relationship with Product before deleting
            subCategory.setProduct(null);
            subCategoriesRepository.save(subCategory);  // Save to reflect changes before deletion
            subCategoriesRepository.deleteById(id);
            return !subCategoriesRepository.existsById(id);
        }
        return false;
    }
}
