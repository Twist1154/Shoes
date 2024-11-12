package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.CartItem;
import za.ac.cput.repository.CartItemRepository;

import java.util.List;

/**
 * CartItemService.java
 *
 * Service implementation for managing CartItem entities.
 * Implements methods for CRUD operations and additional query methods.
 *
 * Author: Rethabile Ntsekhe
 * Student Number: 220455430
 * Date: 25-Aug-24
 */

@Service
@Transactional
public class CartItemService implements ICartItem {

    private final CartItemRepository repository;

    @Autowired
    public CartItemService(CartItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new CartItem in the database.
     *
     * @param cartItem the CartItem entity to be created
     * @return the created CartItem entity
     */
    @Override
    public CartItem create(CartItem cartItem) {
        return repository.save(cartItem);
    }

    /**
     * Reads a CartItem from the database by its ID.
     *
     * @param id the ID of the CartItem to be read
     * @return the CartItem entity with the given ID, or null if not found
     */
    @Override
    public CartItem read(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Updates an existing CartItem in the database.
     *
     * @param cartItem the CartItem entity with updated details
     * @return the updated CartItem entity
     * @throws IllegalArgumentException if the CartItem with the given ID does not exist
     */
    @Override
    public CartItem update(CartItem cartItem) {
        CartItem existingCartItem = repository.findById(cartItem.getId()).orElse(null);
        if (existingCartItem != null) {
            CartItem updatedCartItem = new CartItem.Builder()
                    .copy(existingCartItem)
                    .setCart(cartItem.getCart())
                    .setProduct(cartItem.getProduct())
                    .setProductSku(cartItem.getProductSku())
                    .setQuantity(cartItem.getQuantity())
                    .build();
            return repository.save(updatedCartItem);
        } else {
            throw new IllegalArgumentException("Attempt to update a non-existent cart item with ID: " + cartItem.getId());
        }
    }

    /**
     * Deletes a CartItem from the database by its ID.
     *
     * @param id the ID of the CartItem to be deleted
     * @return
     */
    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("Attempt to delete a non-existent cart item with ID: " + id);
        }
    }

    /**
     * Finds all CartItems in the database.
     *
     * @return a list of all CartItems
     */
    @Override
    public List<CartItem> findAll() {
        return repository.findAll();
    }

    /**
     * Finds CartItems by their associated Cart ID.
     *
     * @param cartId the ID of the Cart to search by
     * @return a list of CartItems associated with the given Cart ID
     */
    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return repository.findByCartId(cartId);
    }

    /**
     * Finds CartItems by their associated Product ID.
     *
     * @param productId the ID of the Product to search by
     * @return a list of CartItems associated with the given Product ID
     */
    @Override
    public List<CartItem> findByProductId(Long productId) {
        return repository.findByProductId(productId);
    }

    /**
     * Finds CartItems by their associated Product SKU ID.
     *
     * @param productSkuId the ID of the Product SKU to search by
     * @return a list of CartItems associated with the given Product SKU ID
     */
    @Override
    public List<CartItem> findByProductSkuId(Long productSkuId) {
        return repository.findByProductSkuId(productSkuId);
    }

    /**
     * Finds CartItems by their quantity.
     *
     * @param quantity the quantity of items to search by
     * @return a list of CartItems with the given quantity
     */
    @Override
    public List<CartItem> findByQuantity(int quantity) {
        return repository.findByQuantity(quantity);
    }

    @Override
    public void deleteByCartId(Long cartId) {
        repository.deleteByCartId(cartId);
    }
}
