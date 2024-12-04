package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * CartService.java
 *
 * Service implementation for managing Cart entities.
 * Implements ICart to provide CRUD operations and additional query methods.
 *
 * @author
 * Rethabile Ntsekhe
 */
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

    @Override
    public Cart create(Cart cart) {
        try {
            // Calculate the total price
            double total = cart.getCartItems().stream()
                    .mapToDouble(item -> item.getProductSku().getPrice() * item.getQuantity())
                    .sum();

            Cart totalCart = new Cart.Builder()
                    .copy(cart)
                    .setTotal(total)
                    .build();

            return repository.save(totalCart);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Cart: " + e.getMessage(), e);
        }
    }

    @Override
    public Cart read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + id));
    }

    @Override
    @Transactional
    public Cart update(Cart cartDetails) {
        try {
            Optional<Cart> existingCartOpt = repository.findById(cartDetails.getId());
            if (existingCartOpt.isPresent()) {
                Cart existingCart = existingCartOpt.get();
                Cart updatedCart = new Cart.Builder()
                        .copy(existingCart)
                        .setTotal(cartDetails.getTotal())
                        .setUpdatedAt(LocalDateTime.now())
                        .build();
                return repository.save(updatedCart);
            } else {
                throw new RuntimeException("Cart not found with ID: " + cartDetails.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating Cart: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Cart not found with ID: " + id);
        }
    }

    @Override
    public List<Cart> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Cart> findByCreatedAtAfter(LocalDateTime createdAt) {
        return repository.findByCreatedAtAfter(createdAt);
    }

    @Override
    public List<Cart> findByTotalGreaterThan(Double total) {
        return repository.findByTotalGreaterThan(total);
    }

    @Override
    public List<Cart> findByUpdatedAtAfter(LocalDateTime updatedAt) {
        return repository.findByUpdatedAtAfter(updatedAt);
    }

    @Override
    public List<Cart> findCartsCreatedWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findCartsCreatedWithinDateRange(startDate, endDate);
    }

    @Override
    public Cart findCartWithHighestTotal() {
        return Optional.ofNullable(repository.findCartWithHighestTotal())
                .orElseThrow(() -> new RuntimeException("No Cart found with the highest total."));
    }

    @Override
    public List<Cart> findCartsCreatedInLast30Days(LocalDateTime thirtyDaysAgo) {
        return repository.findCartsCreatedInLast30Days(thirtyDaysAgo);
    }

    @Override
    public List<Cart> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime createdAt) {
        return repository.findByUserIdAndCreatedAtAfter(userId, createdAt);
    }

    @Override
    public List<Cart> findByUserIdAndUpdatedAtAfter(Long userId, LocalDateTime updatedAt) {
        return repository.findByUserIdAndUpdatedAtAfter(userId, updatedAt);
    }

    @Override
    public List<Cart> findByCreatedAtBefore(LocalDateTime createdAt) {
        return repository.findByCreatedAtBefore(createdAt);
    }

    @Override
    public List<Cart> findByUpdatedAtBefore(LocalDateTime updatedAt) {
        return repository.findByUpdatedAtBefore(updatedAt);
    }


    public List<Cart> findCartsCreatedInLast30Days() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return repository.findByCreatedAtAfter(thirtyDaysAgo);
    }

    @Override
    public void deleteByUserId(Long userId) {
        try {
            List<Cart> userCarts = repository.findByUserId(userId);
            repository.deleteAll(userCarts);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Carts for user ID: " + userId, e);
        }
    }

    public Page<Cart> getPaginatedCarts(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
