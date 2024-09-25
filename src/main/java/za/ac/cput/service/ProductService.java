package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.ProductSubCategories;
import za.ac.cput.repository.ProductRepository;

import java.util.List;

/**
 * ProductService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class ProductService implements IProduct {

    private final ProductRepository productRepository;
    private final ProductSubCategoryService productSubCategoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSubCategoryService productSubCategoryService) {
        this.productRepository = productRepository;
        this.productSubCategoryService = productSubCategoryService;
    }

    @Override
    public Product create(Product product) {
        // Save the product first to generate an ID
        Product savedProduct = productRepository.save(product);

        // If product has sub-categories, save them and associate them with the product
        if (product.getProductSubCategories() != null) {
            product.getProductSubCategories().forEach(subCategory -> {
                subCategory.setProduct(savedProduct); // Set the product reference in the sub-category
                productSubCategoryService.create(subCategory); // Save sub-category
            });
        }
        return savedProduct;
    }

    @Override
    public Product read(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct != null) {
            Product updatedProduct = new Product.Builder()
                    .copy(existingProduct)
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .setSummary(product.getSummary())
                    .setCover(product.getCover())
                    .setImageUrls(product.getImageUrls())
                    .setProductSubCategories(product.getProductSubCategories()) // Set updated sub-categories
                    .setCreatedAt(product.getCreatedAt())
                    .setDeletedAt(product.getDeletedAt())
                    .build();

            // Manage the bidirectional relationship
            if (updatedProduct.getProductSubCategories() != null) {
                updatedProduct.getProductSubCategories().forEach(subCategory -> {
                    subCategory.setProduct(updatedProduct);
                    productSubCategoryService.update(subCategory); // Update sub-categories
                });
            }

            return productRepository.save(updatedProduct);
        } else {
            log.warn("Attempt to update a non-existent product with ID: {}", product.getId());
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        // Before deleting the product, ensure all its sub-categories are dissociated
        Product product = productRepository.findById(id).orElse(null);
        if (product != null && product.getProductSubCategories() != null) {
            product.getProductSubCategories().forEach(subCategory -> {
                subCategory.setProduct(null); // Remove product reference from sub-categories
                productSubCategoryService.update(subCategory);
            });
        }

        productRepository.deleteById(id);
        return !productRepository.existsById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
