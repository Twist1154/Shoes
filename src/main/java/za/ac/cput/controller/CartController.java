package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.service.CartService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CartController.java
 *
 * This class handles HTTP requests related to carts.
 * It provides endpoints for CRUD operations on carts.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Creates a new cart.
     *
     * @param cart the cart to be created
     * @return ResponseEntity containing the created Cart and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.create(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    /**
     * Retrieves a cart by its ID.
     *
     * @param id the ID of the cart to retrieve
     * @return ResponseEntity containing the Cart if found, or a 404 Not Found status if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.read(id);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing cart.
     *
     * @param id   the ID of the cart to be updated
     * @param cart the updated cart details
     * @return ResponseEntity containing the updated Cart and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        Cart updatedCart = cartService.update( cart);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a cart by its ID.
     *
     * @param id the ID of the cart to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all carts.
     *
     * @return ResponseEntity containing the list of all Carts and HTTP status code
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> cartList = cartService.findAll();
        return ResponseEntity.ok(cartList);
    }

    /**
     * Retrieves all carts associated with a specific user ID.
     *
     * @param userId the user ID to search by
     * @return ResponseEntity containing the list of Carts associated with the given user ID
     */
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable Long userId) {
        List<Cart> carts = cartService.findByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    /**
     * Retrieves all carts created after a specific date.
     *
     * @param date the date to search by
     * @return ResponseEntity containing the list of Carts created after the given date
     */
    @GetMapping("/created-after/{date}")
    public ResponseEntity<List<Cart>> getCartsCreatedAfter(@PathVariable String date) {
        LocalDateTime createdAt = LocalDateTime.parse(date);
        List<Cart> carts = cartService.findByCreatedAtAfter(createdAt);
        return ResponseEntity.ok(carts);
    }

    /**
     * Retrieves all carts with a total greater than a specified amount.
     *
     * @param total the minimum total value to search by
     * @return ResponseEntity containing the list of Carts with a total greater than the specified amount
     */
    @GetMapping("/total-greater-than/{total}")
    public ResponseEntity<List<Cart>> getCartsByTotalGreaterThan(@PathVariable Double total) {
        List<Cart> carts = cartService.findByTotalGreaterThan(total);
        return ResponseEntity.ok(carts);
    }

    /**
     * Retrieves all carts updated after a specific date.
     *
     * @param date the date to search by
     * @return ResponseEntity containing the list of Carts updated after the given date
     */
    @GetMapping("/updated-after/{date}")
    public ResponseEntity<List<Cart>> getCartsUpdatedAfter(@PathVariable String date) {
        LocalDateTime updatedAt = LocalDateTime.parse(date);
        List<Cart> carts = cartService.findByUpdatedAtAfter(updatedAt);
        return ResponseEntity.ok(carts);
    }
}
