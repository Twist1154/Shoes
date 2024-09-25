package za.ac.cput.service;

import za.ac.cput.domain.ProductSubCategories;

/**
 * IProductSubCategories.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Sep-24
 */

public interface IProductSubCategories extends IService<ProductSubCategories, Long>{

    ProductSubCategories findById(Long id);
}
