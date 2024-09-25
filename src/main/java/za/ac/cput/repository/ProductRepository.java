package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Product} entity.
 * Provides methods to perform CRUD operations on Product entities.
 *
 * @autor Rethabile Ntsekhe
 * @date 25-Aug-24
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findByProductSubCategories_Id(Long subCategoryId);
}
