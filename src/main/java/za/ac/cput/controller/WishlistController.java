package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.service.WishlistService;

import java.util.List;

/**
 * WishlistController.java
 *
 * Controller for managing Wishlist-related operations.
 * Provides endpoints for creating, reading, updating, and deleting wishlists.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * Creates a new wishlist.
     *
     * @param wishlist the wishlist to be created
     * @return ResponseEntity containing the created Wishlist and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) {
        Wishlist createdWishlist = wishlistService.create(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWishlist);
    }

    /**
     * Retrieves a wishlist by its ID.
     *
     * @param id the ID of the wishlist to retrieve
     * @return ResponseEntity containing the Wishlist if found, or a 404 Not Found status if not found
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long id) {
        Wishlist wishlist = wishlistService.read(id);
        if (wishlist != null) {
            return ResponseEntity.ok(wishlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing wishlist.
     *
     * @param id the ID of the wishlist to update
     * @param wishlist the updated wishlist details
     * @return ResponseEntity containing the updated Wishlist and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Long id, @RequestBody Wishlist wishlist) {
        Wishlist updatedWishlist = wishlistService.update(wishlist);
        if (updatedWishlist != null) {
            return ResponseEntity.ok(updatedWishlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a wishlist by its ID.
     *
     * @param id the ID of the wishlist to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all wishlists.
     *
     * @return ResponseEntity containing the list of all Wishlists and HTTP status code
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Wishlist>> getAllWishlists() {
        List<Wishlist> wishlists = wishlistService.findAll();
        return ResponseEntity.ok(wishlists);
    }

    /**
     * Retrieves all wishlists by user ID.
     *
     * @param userId the ID of the user whose wishlists are to be retrieved
     * @return ResponseEntity containing the list of Wishlists, or a 404 Not Found status if not found
     */
    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistsByUserId(@PathVariable Long userId) {
        List<Wishlist> wishlists = wishlistService.findByUserId(userId);
        if (wishlists != null) {
            return ResponseEntity.ok(wishlists);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
