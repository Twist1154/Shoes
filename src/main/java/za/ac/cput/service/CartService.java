package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CartService.java
 *
 * Service implementation for managing Cart entities.
 * Implements ICartService to provide basic CRUD operations and additional query methods.
 *
 * @autor Rethabile Ntsekhe
 */

@Slf4j
@Service
@Transactional
public class CartService implements ICart {

    private final CartRepository repository;
    private final CartItemService cartItemService;

    @Autowired
    public CartService(CartRepository repository, CartItemService cartItemService) {
        this.repository = repository;
        this.cartItemService = cartItemService;
    }

    /**
     * Creates a new Cart.
     *
     * @param cart the Cart entity to be created
     * @return the created Cart
     */
    @Override
    public Cart create(Cart cart) {
        // Calculate the total price
        double total = 0.0;
        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getProductSku().getPrice() * cartItem.getQuantity();
        }

        Cart totalCart = new Cart.Builder()
                .copy(cart)
                .setTotal(total)
                .build();

        // Save and return the cart
        return repository.save(totalCart);
    }

    /**
     * Reads a Cart by its ID.
     *
     * @param id the ID of the Cart to be read
     * @return the Cart entity if found, or null if not found
     */
    @Override
    public Cart read(Long id) {
        return repository.findById(id).orElse(null);

    }


    /**
     * Updates an existing Cart.
     *
     * @param cartDetails the Cart entity to be updated
     * @return the updated Cart entity, or null if the Cart does not exist
     */
   @Override
   @Transactional(readOnly = false)
    public Cart update(Cart cartDetails) {
           if (repository.existsById(cartDetails.getId())) {
               Cart existingcartDetails = repository.findById(cartDetails.getId()).orElse(null);
               if (existingcartDetails != null) {
                   Cart cartDetailsToUpdate = new Cart.Builder()
                            .copy(existingcartDetails)
                           .setId(existingcartDetails.getId())
                           .setUser(existingcartDetails.getUser())
                           .setTotal(cartDetails.getTotal())
                           .setCreatedAt(existingcartDetails.getCreatedAt())
                            .setUpdatedAt(LocalDateTime.now())
                           .build();
                   return repository.save(cartDetailsToUpdate);
               }
           }
           return null;

   }

    /**
     * Deletes a Cart and cart items by its Cart ID.
     *
     * @param id the ID of the Cart to be deleted
     * @return true if deteled successfully, otherwise false
     */
    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


    public Page<Cart> getPaginatedCarts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Finds all Carts in the database.
     *
     * @return a list of all Cart entities
     */
    @Override
    public List<Cart> findAll() {
        return repository.findAll();
    }

    /**
     * Finds all Carts associated with a specific user ID.
     *
     * @param userId the user ID to search by
     * @return a list of Carts associated with the given user ID
     */
    @Override
    public List<Cart> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Finds all Carts created after a specific date.
     *
     * @param createdAt the date to search by
     * @return a list of Carts created after the given date
     */
    @Override
    public List<Cart> findByCreatedAtAfter(LocalDateTime createdAt) {
        return repository.findByCreatedAtAfter(createdAt);
    }

    /**
     * Finds all Carts with a total greater than a specified amount.
     *
     * @param total the minimum total value to search by
     * @return a list of Carts with a total greater than the specified amount
     */
    @Override
    public List<Cart> findByTotalGreaterThan(Double total) {
        return repository.findByTotalGreaterThan(total);
    }

    /**
     * Finds all Carts that were updated after a certain date.
     *
     * @param updatedAt the date to search by
     * @return a list of Carts updated after the given date
     */
    @Override
    public List<Cart> findByUpdatedAtAfter(LocalDateTime updatedAt) {
        return repository.findByUpdatedAtAfter(updatedAt);
    }

    /**
     * Finds all Carts created within a specific date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of Carts created within the date range
     */
    @Override
    public List<Cart> findCartsCreatedWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findCartsCreatedWithinDateRange(startDate, endDate);
    }

    /**
     * Finds the Cart with the highest total.
     *
     * @return the Cart with the highest total
     */
    @Override
    public Cart findCartWithHighestTotal() {
        return repository.findCartWithHighestTotal();
    }

    /**
     * Finds all Carts with a total greater than a specified amount using a native query.
     *
     * @param total the minimum total value to search by
     * @return a list of Carts with a total greater than the specified amount
     */
    @Override
    public List<Cart> findCartsWithTotalGreaterThan(Double total) {
        return repository.findCartsWithTotalGreaterThan(total);
    }

    /**
     * Finds all Carts associated with a specific user ID and created after a specific date.
     *
     * @param userId the user ID to search by
     * @param createdAt the date to search by
     * @return a list of Carts associated with the given user ID and created after the given date
     */
    @Override
    public List<Cart> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime createdAt) {
        return repository.findByUserIdAndCreatedAtAfter(userId, createdAt);
    }

    /**
     * Finds all Carts associated with a specific user ID and updated after a specific date.
     *
     * @param userId the user ID to search by
     * @param updatedAt the date to search by
     * @return a list of Carts associated with the given user ID and updated after the given date
     */
    @Override
    public List<Cart> findByUserIdAndUpdatedAtAfter(Long userId, LocalDateTime updatedAt) {
        return repository.findByUserIdAndUpdatedAtAfter(userId, updatedAt);
    }

    /**
     * Finds all Carts created before a specific date.
     *
     * @param createdAt the date to search by
     * @return a list of Carts created before the given date
     */
    @Override
    public List<Cart> findByCreatedAtBefore(LocalDateTime createdAt) {
        return repository.findByCreatedAtBefore(createdAt);
    }

    /**
     * Finds all Carts updated before a specific date.
     *
     * @param updatedAt the date to search by
     * @return a list of Carts updated before the given date
     */
    @Override
    public List<Cart> findByUpdatedAtBefore(LocalDateTime updatedAt) {
        return repository.findByUpdatedAtBefore(updatedAt);
    }

    /**
     * Finds all Carts created in the last 30 days.
     *
     * @return a list of Carts created in the last 30 days
     */
    @Override
    public List<Cart> findCartsCreatedInLast30Days() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return repository.findByCreatedAtAfter(thirtyDaysAgo);
    }

    /**
     * Deletes all Carts associated with a specific user ID.
     *
     * @param userId the user ID of the Carts to be deleted
     */
    @Override
    public void deleteByUserId(Long userId) {
        List<Cart> userCarts = repository.findByUserId(userId);
        for (Cart cart : userCarts) {
            repository.delete(cart);

        }
    }
}
