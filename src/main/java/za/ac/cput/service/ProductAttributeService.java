package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.repository.ProductAttributeRepository;

import java.util.List;

/**
 * ProductAttributeService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

@Slf4j
@Service
@Transactional
public class ProductAttributeService implements IProductAttribute {

    private final ProductAttributeRepository repository;

    @Autowired
    public ProductAttributeService(ProductAttributeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductAttribute create(ProductAttribute productAttribute) {
        return repository.save(productAttribute);
    }

    @Override
    public ProductAttribute read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ProductAttribute update(ProductAttribute productAttribute) {
        ProductAttribute existingProductAttribute = repository.findById(productAttribute.getId()).orElse(null);

        if (existingProductAttribute != null) {
            ProductAttribute updatedProductAttribute = new ProductAttribute.Builder()
                    .copy(productAttribute)
                    .setType(productAttribute.getType())
                    .setValue(productAttribute.getValue())
                    .build();
            return repository.save(updatedProductAttribute);
        }
        log.warn("Attempt to update a non-existent product attribute with ID: {}", productAttribute.getId());
        return null;
    }


    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent product Attribute with ID: " + id);
            return false;
        }
    }

    @Override
    public List<ProductAttribute> findAll() {
        return repository.findAll();
    }
}
