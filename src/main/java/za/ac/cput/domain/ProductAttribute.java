package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;
import za.ac.cput.enums.ProductAttributeType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an attribute of a product.
 * This entity class is mapped to the "product_attributes" table in the database.
 * It is immutable and uses the builder pattern for construction.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@Entity
@Getter
@Table(name = "product_attributes")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductAttributeType type;

    @Column(nullable = false)
    private String value;


    public ProductAttribute() {
    }

    private ProductAttribute(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.value = builder.value;
    }

    @Override
    public String toString() {
        return "\n ProductAttribute{" +
                "id=" + id +
                ", type=" + type +
                ", value='" + value + '\'' +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttribute that = (ProductAttribute) o;
        return Objects.equals(id, that.id) &&
                type == that.type &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, value);
    }

    public static class Builder {
        private Long id;
        private ProductAttributeType type;
        private String value;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setType(ProductAttributeType type) {
            this.type = type;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder copy(ProductAttribute productAttribute) {
            this.id = productAttribute.id;
            this.type = productAttribute.type;
            this.value = productAttribute.value;
            return this;
        }

        public ProductAttribute build() {
            return new ProductAttribute(this);
        }
    }
}