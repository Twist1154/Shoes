package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cart entry in the system.
 * Each entry is associated with a User.
 * <p>
 * This entity class is mapped to the "cart" table in the database.
 * Includes necessary mappings for relationships to other entities.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */
@Entity
@Getter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties({"id", "firstName", "lastName", "email"})
    private User user;

    private Double total;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
    }

    private Cart(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.total = builder.total;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.cartItems.addAll(new ArrayList<>(builder.cartItems));
    }


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "\n Cart{" +
                "id=" + id +
                ", user=" + user.getFirstName() +
                ", total=" + total +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(user, cart.user) &&
                Objects.equals(total, cart.total) &&
                Objects.equals(createdAt, cart.createdAt) &&
                Objects.equals(updatedAt, cart.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, total, createdAt, updatedAt);
    }

    public static class Builder {
        private Long id;
        private User user;
        private Double total;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<CartItem> cartItems = new ArrayList<>();

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setTotal(Double total) {
            this.total = total;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setCartItems(List<CartItem> cartItems) {
            this.cartItems = new ArrayList<>(cartItems);  // defensive copy
            return this;
        }

        public Builder copy(Cart cart) {
            this.id = cart.getId();
            this.user = cart.getUser();
            this.total = cart.getTotal();
            this.createdAt = cart.getCreatedAt();
            this.updatedAt = cart.getUpdatedAt();
            this.cartItems = new ArrayList<>(cart.getCartItems());
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }

    }
}
