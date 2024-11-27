package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.ProductSku;
import za.ac.cput.repository.ProductSkuRepository;

import java.util.List;

/**
 * ProductSkuService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

@Slf4j
@Service
@Transactional // Default to transactional for write operations
public class ProductSkuService implements IProductSku {

    private final ProductSkuRepository repository;

    @Autowired
    public ProductSkuService(ProductSkuRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false) // Ensure session is active for create
    public ProductSku create(ProductSku productSku) {
        return repository.save(productSku);
    }

    @Override
    @Transactional(readOnly = true) // Read operations should not require a write session
    public ProductSku read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false) // Write operations should require a transactional session
    public ProductSku update(ProductSku productSku) {
        ProductSku existingProductSku = repository.findById(productSku.getId()).orElse(null);

        if (existingProductSku != null) {
            ProductSku updatedProductSku = new ProductSku.Builder()
                    .copy(existingProductSku)
                    .setId(existingProductSku.getId())
                    .setProduct(productSku.getProduct())
                    .setSizeAttribute(productSku.getSizeAttribute())
                    .setColorAttribute(productSku.getColorAttribute())
                    .setBrandAttribute(productSku.getBrandAttribute())
                    .setSku(productSku.getSku())
                    .setPrice(productSku.getPrice())
                    .setQuantity(productSku.getQuantity())
                    .build();
            return repository.save(updatedProductSku);
        } else {
            log.warn("Attempt to update a non-existent product SKU with ID: " + productSku.getId());
            return null;
        }
    }

    @Override
    @Transactional // Transactions are needed for delete operations
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent product SKU with ID: " + id);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true) // Read-only for list operations
    public List<ProductSku> findAll() {
        return repository.findAll();
    }
}
