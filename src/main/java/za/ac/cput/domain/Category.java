package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a categories entry in the system.
 *
 * This entity class is mapped to the "categories" table in the database.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */

@Entity
@Getter
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("categoryReference")
    @JsonIgnore
    private List<SubCategory> subCategories;

    public Category() {}

    private Category(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
    @Override
    public String toString() {
        return "\n Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subCategories=" + subCategories.get(0).getId() +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(subCategories, category.subCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,subCategories);
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<SubCategory> subCategories;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSubCategories(List<SubCategory> subCategories) {
            this.subCategories = subCategories;
            return this;
        }

        public Builder copy(Category category) {
            this.id = category.getId();
            this.name = category.getName();
            this.subCategories = category.getSubCategories();
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}