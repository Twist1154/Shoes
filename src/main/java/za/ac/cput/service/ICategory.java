package za.ac.cput.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.ac.cput.domain.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ICategoryService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

public interface ICategory extends IService<Category, Long> {
    /**
     * Finds all categories with a specific name.
     *
     * @param name the name of the category to search by
     * @return a list of categories with the given name
     */
    List<Category> findByName(String name);

    /**
     * Finds all categories with a name containing a specific keyword.
     *
     * @param keyword the keyword to search for in category names
     * @return a list of categories with names containing the keyword
     */
    List<Category> findByNameContaining(String keyword);


}
