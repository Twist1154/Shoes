package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.domain.WishlistItem;
import za.ac.cput.domain.Wishlist;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Factory class for creating instances of {@link Wishlist}.
 * Provides static methods to create {@link Wishlist} objects from various inputs.
 *
 * Author: Rethabile Ntsekhe
 * Date: 24-Aug-24
 */
public class WishlistFactory {

    /**
     * Creates a {@link Wishlist} instance from various input parameters.
     *
     * @param user         the {@link User} entity associated with this wishlist
     * @param wishlistItems the list of {@link WishlistItem} associated with this wishlist
     * @param createdAt    the date when the wishlist was created
     * @param deletedAt    the date when the wishlist was deleted (if applicable)
     * @return a new {@link Wishlist} object with properties set from the input parameters
     */
    public static Wishlist createWishlist(
            Long id,
            User user,
            List<WishlistItem> wishlistItems,
            LocalDateTime createdAt,
            LocalDateTime deletedAt
    ) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null in WishlistFactory");
        }
        if (wishlistItems == null || wishlistItems.isEmpty()) {
            throw new IllegalArgumentException("Wishlist must have at least one item");
        }

        return new Wishlist.Builder()
                .setId(id)
                .setUser(user)
                .setWishlistItems(wishlistItems)
                .setCreatedAt(createdAt)
                .setDeletedAt(deletedAt)
                .build();
    }
}
