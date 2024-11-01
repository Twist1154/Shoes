package za.ac.cput.service;

import za.ac.cput.domain.WishlistItem;

import java.util.List;

/**
 * IWishListItemsServivce.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 22-Sep-24
 */

public interface IWishlistItems extends IService<WishlistItem, Long> {
    List<WishlistItem> findByWishlist_Id(Long wishlistId);

    void deleteByWishlistId(Long wishlistId);
}
