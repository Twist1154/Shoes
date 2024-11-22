package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.CartItem;
import za.ac.cput.service.CartItemService;

import java.util.List;

/**
 * CartItemController.java
 *
 * This class handles HTTP requests related to cart items.
 * It provides endpoints for CRUD operations on cart items.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * Creates a new cart item.
     *
     * @param cartItem the CartItem object to be created
     * @return ResponseEntity containing the created CartItem and HTTP status code 201 Created
     */
    @PostMapping("/create")
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemService.create(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCartItem);
    }

    /**
     * Retrieves a cart item by its ID.
     *
     * @param id the ID of the CartItem to retrieve
     * @return ResponseEntity containing the CartItem if found, or HTTP status 404 Not Found if not
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        CartItem cartItem = cartItemService.read(id);
        if (cartItem != null) {
            return ResponseEntity.ok(cartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing cart item.
     *
     * @param id the ID of the CartItem to be updated
     * @param cartItem the CartItem object with updated details
     * @return ResponseEntity containing the updated CartItem and HTTP status code 200 OK, or HTTP status 404 Not Found if the item does not exist
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        CartItem updatedCartItem = cartItemService.update(cartItem);
        if (updatedCartItem != null) {
            return ResponseEntity.ok(updatedCartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a cart item by its ID.
     *
     * @param id the ID of the CartItem to delete
     * @return ResponseEntity with HTTP status code 204 No Content to indicate successful deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all cart items.
     *
     * @return ResponseEntity containing a list of all CartItems and HTTP status code 200 OK
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        List<CartItem> cartItemList = cartItemService.findAll();
        return ResponseEntity.ok(cartItemList);
    }

    /**
     * Retrieves cart items by their associated Cart ID.
     *
     * @param cartId the ID of the Cart to search by
     * @return ResponseEntity containing a list of CartItems associated with the given Cart ID
     */
    @GetMapping("/byCart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartItemService.findByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    /**
     * Retrieves cart items by their associated Product ID.
     *
     * @param productId the ID of the Product to search by
     * @return ResponseEntity containing a list of CartItems associated with the given Product ID
     */
    @GetMapping("/byProduct/{productId}")
    public ResponseEntity<List<CartItem>> getCartItemsByProductId(@PathVariable Long productId) {
        List<CartItem> cartItems = cartItemService.findByProductId(productId);
        return ResponseEntity.ok(cartItems);
    }

    /**
     * Retrieves cart items by their associated Product SKU ID.
     *
     * @param productSkuId the ID of the Product SKU to search by
     * @return ResponseEntity containing a list of CartItems associated with the given Product SKU ID
     */
    @GetMapping("/bySku/{productSkuId}")
    public ResponseEntity<List<CartItem>> getCartItemsByProductSkuId(@PathVariable Long productSkuId) {
        List<CartItem> cartItems = cartItemService.findByProductSkuId(productSkuId);
        return ResponseEntity.ok(cartItems);
    }

    /**
     * Retrieves cart items by their quantity.
     *
     * @param quantity the quantity of items to search by
     * @return ResponseEntity containing a list of CartItems with the given quantity
     */
    @GetMapping("/quantity/{quantity}")
    public ResponseEntity<List<CartItem>> getCartItemsByQuantity(@PathVariable int quantity) {
        List<CartItem> cartItems = cartItemService.findByQuantity(quantity);
        return ResponseEntity.ok(cartItems);
    }

    /**
     * Deletes all cart items associated with a Cart.
     *
     * @param cartId the ID of the Cart to delete items from
     * @return ResponseEntity with HTTP status code 204 No Content to indicate successful deletion
     */
    @DeleteMapping("/deleteByCart/{cartId}")
    public ResponseEntity<Void> deleteCartItemsByCartId(@PathVariable Long cartId) {
        cartItemService.deleteByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
}
