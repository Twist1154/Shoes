package za.ac.cput.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("categoryReference")
    @JsonIgnore
    private List<SubCategory> subCategories = new ArrayList<>();

    public Category() {
    }

    public Category(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.subCategories = builder.subCategories != null ? builder.subCategories : new ArrayList<>();
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
        return Objects.hash(id, name, subCategories);
    }

    @Override
    public String toString() {
        return "Category{" +
                "Category ID: " + id +
                ", NAME: '" + name + '\'' +
                ", Sub Categories: " + (subCategories != null ? subCategories : "null") +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<SubCategory> subCategories = new ArrayList<>();

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
