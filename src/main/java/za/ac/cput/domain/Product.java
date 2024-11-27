package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a product within the system.
 * This entity class is mapped to the "products" table in the database.
 * <p>
 * author: Rethabile Ntsekhe
 * date: 25-Aug-24
 */
@Entity
@Getter
@Table(name = "products")
public final class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String summary;
    private String cover;

    @Embedded
    private ImageUrls imageUrls;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("productReference")
    @JsonIgnore
    private List<SubCategory> subCategory = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("productReviewReference")
    @JsonIgnore
    private List<Review> review = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Product() {
    }

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.summary = builder.summary;
        this.cover = builder.cover;
        this.imageUrls = builder.imageUrls;
        this.subCategory = builder.subCategory;
        this.createdAt = builder.createdAt;
    }

    @Override
    public String toString() {
        return "\n Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                ", cover='" + cover + '\'' +
                ", images=" + imageUrls +
                ", subCategory=" + (subCategory != null ? subCategory.size() : 0) +
                ", createdAt=" + createdAt +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(summary, product.summary) &&
                Objects.equals(cover, product.cover) &&
                Objects.equals(imageUrls, product.imageUrls) &&
                Objects.equals(subCategory, product.subCategory) &&
                Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, summary, cover, imageUrls, subCategory, createdAt);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String summary;
        private String cover;
        private ImageUrls imageUrls;
        private List<SubCategory> subCategory = new ArrayList<>();
        private LocalDateTime createdAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setCover(String cover) {
            this.cover = cover;
            return this;
        }

        public Builder setImageUrls(ImageUrls imageUrls) {
            this.imageUrls = imageUrls;
            return this;
        }

        public Builder setSubCategory(List<SubCategory> subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.description = product.getDescription();
            this.summary = product.getSummary();
            this.cover = product.getCover();
            this.subCategory = product.getSubCategory();
            this.createdAt = product.getCreatedAt();
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}