package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Product;
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
@Transactional
@Service
public class ProductService implements IProduct {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
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
                    .setId(existingProduct.getId())
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .setSummary(product.getSummary())
                    .setCover(product.getCover())
                    .setImageUrls(product.getImageUrls())
                    .setSubCategory(product.getSubCategory())
                    .setCreatedAt(product.getCreatedAt())
                    .setDeletedAt(product.getDeletedAt())
                    .build();
            return productRepository.save(updatedProduct);
        } else {
            log.warn("Attempt to update a non-existent order item with ID: {}", product.getId());

            return null;
        }
    }

    public boolean delete(Long id) {
        productRepository.deleteById(id);

        // Check if the entity still exists after deletion
        boolean exists = productRepository.existsById(id);

        // Return false if entity was deleted successfully, otherwise return true
        return !exists;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}