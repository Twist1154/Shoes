package za.ac.cput.service;

import za.ac.cput.domain.SubCategory;

import java.util.List;

/**
 * ISubCategoryService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

public interface ISubCategory extends IService<SubCategory, Long>{

    List<SubCategory> findSubCategoriesByCategory_Id(Long categoryId);
    List<SubCategory> findSubCategoriesByProduct_Id(Long productId);
}
