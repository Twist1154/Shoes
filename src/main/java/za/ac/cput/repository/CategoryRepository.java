package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * CategoryRepository.java
 *
 * Repository interface for accessing Category entities from the database.
 * Extends JpaRepository to provide basic CRUD operations.
 * Custom query methods are defined to find Categories by specific fields.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

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
