package za.ac.cput.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.repository.WishlistRepository;

import java.util.List;

/**
 * WishlistService.java
 *
 * This service handles operations for managing Wishlist entities.
 * It includes methods for creating, reading, updating, and deleting Wishlists.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class WishlistService implements iWishlist {

    private final WishlistRepository repository;
    private final WishlistItemService wishListItemService;

    @Autowired
    public WishlistService(WishlistRepository repository, WishlistItemService wishListItemService) {
        this.repository = repository;
        this.wishListItemService = wishListItemService;
    }

    /**
     * Create a new wishlist.
     *
     * @param wishlist the wishlist to create
     * @return the saved wishlist
     */
    @Override
    @Transactional(readOnly = false)
    public Wishlist create(Wishlist wishlist) {
        return repository.save(wishlist);
    }

    /**
     * Read and retrieve a wishlist by its ID.
     * Lazily loads WishlistItems and their related Products to avoid LazyInitializationException.
     *
     * @param id the ID of the wishlist to retrieve
     * @return the found wishlist
     * @throws EntityNotFoundException if the wishlist is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Wishlist read(Long id) {
        Wishlist wishlist = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));

        wishlist.getWishlistItems().size();
        return wishlist;
    }

    /**
     * Update an existing wishlist.
     *
     * @param wishlist the wishlist with updated data
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist with the provided ID does not exist
     */
    @Override
    @Transactional(readOnly = false)
    public Wishlist update(Wishlist wishlist) {
        Wishlist existingWishlist = repository.findById(wishlist.getId()).orElse(null);
        if (existingWishlist != null) {
            Wishlist updatedWishlist = new Wishlist.Builder()
                    .copy(existingWishlist)
                    .setUser(wishlist.getUser())
                    .setWishlistItems(wishlist.getWishlistItems())
                    .build();
            return repository.save(updatedWishlist);
        } else {
            throw new IllegalArgumentException("Wishlist with ID " + wishlist.getId() + " does not exist");
        }
    }

    /**
     * Delete a wishlist and wishlist Items by its ID.
     *
     * @param id the ID of the wishlist to delete
     * @return
     */

    public boolean delete(Long id) {
       if (repository.existsById(id)) {
           repository.deleteById(id);
           return !repository.existsById(id); // Return true if deleted successfully
       } else {
           log.warn("Attempt to delete a non-existent Wishlist with ID: " + id);
           return false;
       }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Retrieve all wishlists.
     *
     * @return a list of all wishlists
     */
    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> findAll() {
        return repository.findAll();
    }
}
