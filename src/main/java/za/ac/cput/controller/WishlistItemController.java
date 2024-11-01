package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.WishlistItem;
import za.ac.cput.service.WishlistItemService;

import java.util.List;

/**
 * WishlistItemController.java
 *
 * Controller for managing WishlistItems.
 * Provides endpoints for creating, reading, updating, and deleting wishlist items.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 22-Sep-24
 */

@RestController
@RequestMapping("/wishlist/items")
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    @Autowired
    public WishlistItemController(WishlistItemService wishlistItemService) {
        this.wishlistItemService = wishlistItemService;
    }

    /**
     * Creates a new wishlist item.
     *
     * @param wishlistItem the wishlist item to be created
     * @return ResponseEntity containing the created WishlistItem and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<WishlistItem> createWishlistItem(@RequestBody WishlistItem wishlistItem) {
        WishlistItem createdItem = wishlistItemService.create(wishlistItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    /**
     * Retrieves a wishlist item by its ID.
     *
     * @param id the ID of the wishlist item to retrieve
     * @return ResponseEntity containing the WishlistItem if found, or a 404 Not Found status if not found
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<WishlistItem> getWishlistItemById(@PathVariable Long id) {
        WishlistItem wishlistItem = wishlistItemService.read(id);
        if (wishlistItem != null) {
            return ResponseEntity.ok(wishlistItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing wishlist item.
     *
     * @param id the ID of the wishlist item to update
     * @param wishlistItem the updated wishlist item details
     * @return ResponseEntity containing the updated WishlistItem and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<WishlistItem> updateWishlistItem(@PathVariable Long id, @RequestBody WishlistItem wishlistItem) {
       // wishlistItem.setId(id); // Set the ID in the wishlistItem object
        WishlistItem updatedItem = wishlistItemService.update(wishlistItem);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * Deletes a wishlist item by its ID.
     *
     * @param id the ID of the wishlist item to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long id) {
        if (wishlistItemService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all wishlist items.
     *
     * @return ResponseEntity containing the list of all WishlistItems and HTTP status code
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<WishlistItem>> getAllWishlistItems() {
        List<WishlistItem> wishlistItems = wishlistItemService.findAll();
        return ResponseEntity.ok(wishlistItems);
    }

    /**
     * Retrieves all wishlist items by wishlist ID.
     *
     * @param wishlistId the ID of the wishlist whose items are to be retrieved
     * @return ResponseEntity containing the list of WishlistItems, or a 404 Not Found status if not found
     */
    @GetMapping("/getByWishlist/{wishlistId}")
    public ResponseEntity<List<WishlistItem>> getWishlistItemsByWishlistId(@PathVariable Long wishlistId) {
        List<WishlistItem> wishlistItems = wishlistItemService.findByWishlist_Id(wishlistId);
        if (!wishlistItems.isEmpty()) {
            return ResponseEntity.ok(wishlistItems);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes all wishlist items by wishlist ID.
     *
     * @param wishlistId the ID of the wishlist whose items are to be deleted
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/deleteByWishlist/{wishlistId}")
    public ResponseEntity<Void> deleteItemsByWishlistId(@PathVariable Long wishlistId) {
        wishlistItemService.deleteByWishlistId(wishlistId);
        return ResponseEntity.noContent().build();
    }
}
