package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.ProductAttribute;
import za.ac.cput.repository.ProductAttributeRepository;

import java.time.LocalDateTime;
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

    private final ProductAttributeRepository productAttributeRepository;

    @Autowired
    public ProductAttributeService(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    @Override
    public ProductAttribute create(ProductAttribute productAttribute) {
        return productAttributeRepository.save(productAttribute);
    }

    @Override
    public ProductAttribute read(Long id) {
        return productAttributeRepository.findById(id).orElse(null);
    }

    @Override
    public ProductAttribute update(ProductAttribute productAttribute) {
        ProductAttribute existingProductAttribute = productAttributeRepository.findById(productAttribute.getId()).orElse(null);

        if (existingProductAttribute != null) {
            ProductAttribute updatedProductAttribute = new ProductAttribute.Builder()
                    .copy(productAttribute)
                    .setType(productAttribute.getType())
                    .setValue(productAttribute.getValue())
                    .build();
            return productAttributeRepository.save(updatedProductAttribute);
        }
        log.warn("Attempt to update a non-existent product attribute with ID: {}", productAttribute.getId());
        return null;
    }


    public boolean delete(Long id) {
        productAttributeRepository.deleteById(id);

        // Check if the entity still exists after deletion
        boolean exists = productAttributeRepository.existsById(id);

        // Return false if entity was deleted successfully, otherwise return true
        return !exists;
    }

    @Override
    public List<ProductAttribute> findAll() {
        return productAttributeRepository.findAll();
    }
}
