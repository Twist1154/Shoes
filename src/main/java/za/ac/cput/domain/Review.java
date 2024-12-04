package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Review.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 24-Sep-24
 */
@Getter
@Entity(name = "review")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String review;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "products_id")
    @JsonManagedReference("productReviewReference")
    @JsonIncludeProperties("id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonManagedReference("userReviewReference")
    @JsonIncludeProperties({"id", "firstName", "lastName", "avatar"})
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(Builder builder) {
        this.id = builder.id;
        this.review = builder.review;
        this.rating = builder.rating;
        this.product = builder.product;
        this.user = builder.user;
        this.createdAt = builder.createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                ", product=" + product.getName() +
                ", User=" + user.getId() +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return rating == review.rating &&
                Objects.equals(id, review.id) &&
                Objects.equals(this.review, review.review) &&
                Objects.equals(product, review.product) &&
                Objects.equals(user, review.user) &&
                Objects.equals(createdAt, review.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, review, rating, product, user, createdAt);
    }

    public static class Builder {
        private Long id;
        private String review;
        private int rating;
        private Product product;
        private User user;
        private LocalDateTime createdAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setReview(String review) {
            this.review = review;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(Review review) {
            this.id = review.id;
            this.review = review.review;
            this.rating = review.rating;
            this.product = review.product;
            this.user = review.user;
            this.createdAt = review.createdAt;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
