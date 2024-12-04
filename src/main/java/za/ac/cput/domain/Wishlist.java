package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a wishlist entry in the system.
 * Each entry is associated with a user and can have multiple products.
 * <p>
 * This entity class is mapped to the "wishlist" table in the database.
 * It uses Lombok annotations to reduce boilerplate code.
 * <p>
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@Entity
@Getter
@Table(name = "wishlist")
public class Wishlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties({"id", "avatar", "Username", "firstName", "lastName"})
    @JsonIgnoreProperties({"email","password", "role","birthDate", "createdAt","phoneNumber"})
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "wishlist",  cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<WishlistItem> wishlistItems = new ArrayList<>();


    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Wishlist() {
    }

    private Wishlist(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.wishlistItems.addAll(new ArrayList<>(builder.wishlistItems));
        this.createdAt = builder.createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) &&
               Objects.equals(user, wishlist.user) &&
               Objects.equals(createdAt, wishlist.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, createdAt);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", user=" + user +
                ", wishlistItems=" + wishlistItems +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {
        private Long id;
        private User user;
        private List<WishlistItem> wishlistItems = new ArrayList<>();
        private LocalDateTime createdAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setWishlistItems(List<WishlistItem> wishlistItems) {
            this.wishlistItems = new ArrayList<>(wishlistItems);
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(Wishlist wishlist) {
            this.id = wishlist.getId();
            this.user = wishlist.getUser();
            this.wishlistItems = new ArrayList<>(wishlist.getWishlistItems());
            this.createdAt = wishlist.getCreatedAt();
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }
}
