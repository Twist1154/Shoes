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

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
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
                    .build();
            return repository.save(updatedProduct);
        } else {
            log.warn("Attempt to update a non-existent order item with ID: {}", product.getId());

            return null;
        }
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent product with ID: " + id);
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }
}