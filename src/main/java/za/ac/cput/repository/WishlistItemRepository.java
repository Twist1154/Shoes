package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.WishlistItem;

import java.util.List;

/**
 * WishlistItemRepository.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 22-Sep-24
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByWishlist_Id(Long wishlistId);

    void deleteByWishlistId(Long wishlistId);
}
