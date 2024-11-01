package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a wishlist entry in the system.
 * Each entry is associated with a user and can have multiple products.
 *
 * This entity class is mapped to the "wishlist" table in the database.
 * It uses Lombok annotations to reduce boilerplate code.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@Entity
@Getter
@ToString
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "wishlist", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WishlistItem> wishlistItems;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Wishlist() {
    }

    private Wishlist(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.wishlistItems = builder.wishlistItems;
        this.createdAt = builder.createdAt;
        this.deletedAt = builder.deletedAt;
    }

/*    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", user=" + user +
                ", wishlistItems=" + wishlistItems +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                "}\n ";
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) &&
                Objects.equals(user, wishlist.user) &&
                Objects.equals(createdAt, wishlist.createdAt) &&
                Objects.equals(deletedAt, wishlist.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, createdAt, deletedAt);
    }

    public static class Builder {
        private Long id;
        private User user;
        private List<WishlistItem> wishlistItems;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setWishlistItems(List<WishlistItem> wishlistItems) {
            this.wishlistItems = wishlistItems;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setDeletedAt(LocalDateTime deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Builder copy(Wishlist wishlist) {
            this.id = wishlist.getId();
            this.user = wishlist.getUser();
            this.wishlistItems = wishlist.getWishlistItems();
            this.createdAt = wishlist.getCreatedAt();
            this.deletedAt = wishlist.getDeletedAt();
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }
}
