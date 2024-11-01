package za.ac.cput.service;

import za.ac.cput.domain.Wishlist;

import java.util.List;

/**
 * iWishlistService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

public interface iWishlist extends IService<Wishlist, Long>{
    List<Wishlist> findByUserId(Long userId);
}
