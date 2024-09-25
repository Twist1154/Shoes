package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_subcategory",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id")
    )
    private List<SubCategory> subCategory;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

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
        this.deletedAt = builder.deletedAt;
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
                ", subCategory=" +  (subCategory != null ? subCategory.size() : 0) +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
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
                Objects.equals(createdAt, product.createdAt) &&
                Objects.equals(deletedAt, product.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, summary, cover, imageUrls, subCategory, createdAt, deletedAt);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String summary;
        private String cover;
        private ImageUrls imageUrls;
        private List<SubCategory> subCategory;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;

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

        public Builder setDeletedAt(LocalDateTime deletedAt) {
            this.deletedAt = deletedAt;
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
            this.deletedAt = product.getDeletedAt();
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}