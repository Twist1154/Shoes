package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Wishlist;

import java.util.List;

/**
 * WishlistRepository.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    // Additional query methods if needed
    List<Wishlist> findByUserId(Long userId);
}
