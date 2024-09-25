package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents the relationship between Product and SubCategory.
 * This entity maps the product_subcategory join table in the database.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Sep-24
 */
@Entity
@Getter
@Setter
@Table(name = "product_subcategory")
public class ProductSubCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subCategory;

    public ProductSubCategories() {
    }

    // Constructor with all fields
    public ProductSubCategories(Long id, Product product, SubCategory subCategory) {
        this.id = id;
        this.product = product;
        this.subCategory = subCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSubCategories that = (ProductSubCategories) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(product, that.product) &&
                Objects.equals(subCategory, that.subCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, subCategory);
    }

    @Override
    public String toString() {
        return "ProductSubCategories{" +
                "id=" + id +
                ", product=" + product.getName() +  // Display product name for clarity
                ", subCategory=" + subCategory.getName() +  // Display sub-category name for clarity
                '}';
    }
}
