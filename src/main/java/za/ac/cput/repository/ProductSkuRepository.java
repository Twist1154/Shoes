package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.ProductSku;

import java.util.Optional;

/**
 * Repository interface for {@link ProductSku} entity.
 * Provides methods to perform CRUD operations on ProductSkuService entities.
 *
 * @autor Rethabile Ntsekhe
 * @date 25-Aug-24
 */

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
    Optional<ProductSku> findById(Long id);

}
