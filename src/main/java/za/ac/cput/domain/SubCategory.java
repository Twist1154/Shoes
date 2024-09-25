package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a sub-category entry in the system.
 * This entity class is mapped to the "sub_categories" table in the database.
 *
 * The class is immutable and uses a builder pattern for construction.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@Entity
@Getter
@Table(name = "sub_categories")
public final class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public SubCategory() {
    }

    private SubCategory(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.name = builder.name;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.deletedAt = builder.deletedAt;
    }



    @Override
    public String toString() {
        return "\n SubCategory{" +
                "id=" + id +
                ", category=" + category+ (category != null ? category.getId() : "null") +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCategory that = (SubCategory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(category, that.category) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, description, createdAt, deletedAt);
    }

    public static class Builder {
        private Long id;
        private Category category;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category;
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

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setDeletedAt(LocalDateTime deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Builder copy(SubCategory subCategory) {
            this.id = subCategory.getId();
            this.category = subCategory.getCategory();
            this.name = subCategory.getName();
            this.description = subCategory.getDescription();
            this.createdAt = subCategory.getCreatedAt();
            this.deletedAt = subCategory.getDeletedAt();
            return this;
        }

        public SubCategory build() {
            return new SubCategory(this);
        }
    }
}