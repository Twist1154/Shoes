package za.ac.cput.service;

import za.ac.cput.domain.Cart;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ICart.java
 *
 * Service interface for managing Cart entities.
 * Extends IService to provide basic CRUD operations.
 * Additional methods are added to reflect the custom repository queries.
 *
 * @author
 * Rethabile Ntsekhe
 * @date 25-Aug-24
 */
public interface ICart extends IService<Cart, Long> {

    /**
     * Finds all Carts associated with a given userId.
     *
     * @param userId the ID of the user to search by
     * @return a list of Carts associated with the given userId
     */
    List<Cart> findByUserId(Long userId);

    /**
     * Finds all Carts created after a certain date.
     *
     * @param createdAt the date to search by
     * @return a list of Carts created after the given date
     */
    List<Cart> findByCreatedAtAfter(LocalDateTime createdAt);

    /**
     * Finds all Carts with a total greater than a specified amount.
     *
     * @param total the minimum total value to search by
     * @return a list of Carts with a total greater than the specified amount
     */
    List<Cart> findByTotalGreaterThan(Double total);

    /**
     * Finds all Carts that were updated after a certain date.
     *
     * @param updatedAt the date to search by
     * @return a list of Carts updated after the given date
     */
    List<Cart> findByUpdatedAtAfter(LocalDateTime updatedAt);

    /**
     * Finds all Carts created within a specific date range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of Carts created within the date range
     */
    List<Cart> findCartsCreatedWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Finds the Cart with the highest total.
     *
     * @return the Cart with the highest total
     */
    Cart findCartWithHighestTotal();

    /**
     * Finds all Carts associated with a user and created after a certain date.
     *
     * @param userId    the ID of the user to search by
     * @param createdAt the date to search by
     * @return a list of Carts associated with the given userId and created after the given date
     */
    List<Cart> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime createdAt);

    /**
     * Finds all Carts associated with a user and updated after a certain date.
     *
     * @param userId    the ID of the user to search by
     * @param updatedAt the date to search by
     * @return a list of Carts associated with the given userId and updated after the given date
     */
    List<Cart> findByUserIdAndUpdatedAtAfter(Long userId, LocalDateTime updatedAt);

    /**
     * Finds all Carts created before a certain date.
     *
     * @param createdAt the date to search by
     * @return a list of Carts created before the given date
     */
    List<Cart> findByCreatedAtBefore(LocalDateTime createdAt);

    /**
     * Finds all Carts updated before a certain date.
     *
     * @param updatedAt the date to search by
     * @return a list of Carts updated before the given date
     */
    List<Cart> findByUpdatedAtBefore(LocalDateTime updatedAt);

    /**
     * Finds all Carts created within the last 30 days.
     *
     * @param thirtyDaysAgo the date representing 30 days ago
     * @return a list of Carts created within the last 30 days
     */
    List<Cart> findCartsCreatedInLast30Days(LocalDateTime thirtyDaysAgo);

    /**
     * Deletes all Carts associated with a specific userId.
     *
     * @param userId the ID of the user whose carts should be deleted
     */
    void deleteByUserId(Long userId);
}
