package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * WishlistItem.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 20-Sep-24
 */

@Entity
@Getter
@Table(name = "wish_list_items")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIncludeProperties({"id", "name", "description", "summary", "cover"})
    private Product product;

    @CreationTimestamp
    private LocalDateTime dateAdded;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    public WishlistItem() {
    }

    public WishlistItem(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.dateAdded = builder.dateAdded;
        this.wishlist = builder.wishlist;
    }


    @PrePersist
    public void prePersist() {
        this.dateAdded = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WishlistItem that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product) && Objects.equals(dateAdded, that.dateAdded) && Objects.equals(wishlist, that.wishlist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, dateAdded, wishlist);
    }

    @Override
    public String toString() {
        return "\n WishlistItem{" +
                "id=" + id +
                ", product=" + product.getName() +
                ", dateAdded=" + dateAdded +
                ", wishlist=" + wishlist.getId() +
                "}\n";
    }

    public static class Builder {
        private Long id;
        private Product product;
        private LocalDateTime dateAdded;
        private Wishlist wishlist;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setDateAdded(LocalDateTime dateAdded) {
            this.dateAdded = dateAdded;
            return this;
        }

        public Builder setWishlist(Wishlist wishlist) {
            this.wishlist = wishlist;
            return this;
        }

        public Builder copy(WishlistItem wishlistItem) {
            this.id = wishlistItem.id;
            this.product = wishlistItem.product;
            this.dateAdded = wishlistItem.dateAdded;
            this.wishlist = wishlistItem.wishlist;
            return this;
        }

        public WishlistItem build() {
            return new WishlistItem(this);
        }
    }
}
