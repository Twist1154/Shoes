package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.service.CartService;


import java.time.LocalDateTime;
import java.util.List;

/**
 * CartController.java
 * REST controller for managing Cart entities.
 * Provides endpoints for CRUD operations and advanced queries.
 *
 * @author Rethabile Ntsekhe
 */
@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Create a new cart
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.create(cart));
    }

    // Get a cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.read(id));
    }

    // Update a cart
    @PutMapping("/update")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cartDetails) {
        return ResponseEntity.ok(cartService.update(cartDetails));
    }

    // Delete a cart by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        boolean isDeleted = cartService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get all carts
    @GetMapping("/getAll")
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/getAllPaginated")
    public Page<Cart> getCarts(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
        return cartService.getPaginatedCarts(pageable);
    }


    // Get all carts by user ID
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

    // Get carts created after a specific date
    @GetMapping("/created-after/{createdAt}")
    public ResponseEntity<List<Cart>> getCartsCreatedAfter(@PathVariable String createdAt) {
        return ResponseEntity.ok(cartService.findByCreatedAtAfter(LocalDateTime.parse(createdAt)));
    }

    // Get carts with total greater than a specific value
    @GetMapping("/total-greater-than/{total}")
    public ResponseEntity<List<Cart>> getCartsWithTotalGreaterThan(@PathVariable Double total) {
        return ResponseEntity.ok(cartService.findByTotalGreaterThan(total));
    }

    // Get carts updated after a specific date
    @GetMapping("/updated-after/{updatedAt}")
    public ResponseEntity<List<Cart>> getCartsUpdatedAfter(@PathVariable String updatedAt) {
        return ResponseEntity.ok(cartService.findByUpdatedAtAfter(LocalDateTime.parse(updatedAt)));
    }

    // Get carts created within a date range
    @GetMapping("/created-between/{startDate}/{endDate}")
    public ResponseEntity<List<Cart>> getCartsCreatedWithinDateRange(
            @PathVariable String startDate, @PathVariable String endDate) {
        return ResponseEntity.ok(cartService.findCartsCreatedWithinDateRange(
                LocalDateTime.parse(startDate), LocalDateTime.parse(endDate)));
    }

    // Get cart with the highest total
    @GetMapping("/highest-total")
    public ResponseEntity<Cart> getCartWithHighestTotal() {
        return ResponseEntity.ok(cartService.findCartWithHighestTotal());
    }

    // Get carts with a total greater than a value using a native query
    @GetMapping("/native/total-greater-than/{total}")
    public ResponseEntity<List<Cart>> getCartsWithTotalGreaterThanNative(@PathVariable Double total) {
        return ResponseEntity.ok(cartService.findByTotalGreaterThan(total));
    }

    // Get carts by user ID and created after a specific date
    @GetMapping("/by-user/{userId}/created-after/{createdAt}")
    public ResponseEntity<List<Cart>> getCartsByUserIdAndCreatedAtAfter(
            @PathVariable Long userId, @PathVariable String createdAt) {
        return ResponseEntity.ok(cartService.findByUserIdAndCreatedAtAfter(
                userId, LocalDateTime.parse(createdAt)));
    }

    // Get carts by user ID and updated after a specific date
    @GetMapping("/by-user/{userId}/updated-after/{updatedAt}")
    public ResponseEntity<List<Cart>> getCartsByUserIdAndUpdatedAtAfter(
            @PathVariable Long userId, @PathVariable String updatedAt) {
        return ResponseEntity.ok(cartService.findByUserIdAndUpdatedAtAfter(
                userId, LocalDateTime.parse(updatedAt)));
    }

    // Get carts created before a specific date
    @GetMapping("/created-before/{createdAt}")
    public ResponseEntity<List<Cart>> getCartsCreatedBefore(@PathVariable String createdAt) {
        return ResponseEntity.ok(cartService.findByCreatedAtBefore(LocalDateTime.parse(createdAt)));
    }

    // Get carts updated before a specific date
    @GetMapping("/updated-before/{updatedAt}")
    public ResponseEntity<List<Cart>> getCartsUpdatedBefore(@PathVariable String updatedAt) {
        return ResponseEntity.ok(cartService.findByUpdatedAtBefore(LocalDateTime.parse(updatedAt)));
    }

    // Get carts created in the last 30 days
    @GetMapping("/created-last-30-days")
    public ResponseEntity<List<Cart>> getCartsCreatedInLast30Days() {
        return ResponseEntity.ok(cartService.findCartsCreatedInLast30Days());
    }

    // Delete all carts by user ID
    @DeleteMapping("/delete/by-user/{userId}")
    public ResponseEntity<Void> deleteCartsByUserId(@PathVariable Long userId) {
        cartService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
