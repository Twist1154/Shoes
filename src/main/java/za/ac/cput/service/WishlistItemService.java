package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.WishlistItem;
import za.ac.cput.repository.WishlistItemRepository;

import java.util.List;

/**
 * WishlistItemService.java
 *
 * Service class for managing WishlistItem operations.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 22-Sep-24
 */
@Service
@Transactional
public class WishlistItemService implements IWishlistItems {

    @Autowired
    private final WishlistItemRepository repository;

    @Autowired
    public WishlistItemService(WishlistItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public WishlistItem create(WishlistItem wishListItem) {
        return repository.save(wishListItem);
    }

    @Override
    public WishlistItem read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public WishlistItem update(WishlistItem wishListItem) {
        WishlistItem existingWishlistItem = repository.findById(wishListItem.getId()).orElse(null);

        if (existingWishlistItem != null) {
            // Build the updated item
            WishlistItem updatedItem = new WishlistItem.Builder()
                    .copy(wishListItem)
                    .setId(existingWishlistItem.getId()) // Preserve the ID
                    .setProduct(wishListItem.getProduct())  // Update other fields
                    .setDateAdded(wishListItem.getDateAdded())
                    .setWishlist(wishListItem.getWishlist())
                    .build();

            return repository.save(updatedItem); // Save the updated item
        } else {
            throw new IllegalArgumentException("Wishlist with ID " + wishListItem.getId() + " does not exist");
        }
    }

    @Override
    public List<WishlistItem> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);

        // Check if the entity still exists after deletion
        boolean exists = repository.existsById(id);

        // Return false if entity was deleted successfully, otherwise return true
        return !exists;
    }

    @Override
    public List<WishlistItem> findByWishlist_Id(Long wishlistId) {
        return repository.findByWishlist_Id(wishlistId);
    }

    @Override
    public void deleteByWishlistId(Long wishlistId) {
        repository.deleteByWishlistId(wishlistId);

        boolean exists = repository.existsById(wishlistId);

    }

}
