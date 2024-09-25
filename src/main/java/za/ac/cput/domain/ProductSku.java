package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a SKU (Stock Keeping Unit) for a product.
 * This entity class is mapped to the "products_skus" table in the database.
 * It is immutable and uses the builder pattern for construction.
 *
 * Corrected the field assignment in the builder and constructor.
 *
 * @author Rethabile
 * @date 25-Aug-24
 */
@Entity
@Getter
@Table(name = "products_skus")
public class ProductSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "size_attribute_id", nullable = false)
    private ProductAttribute sizeAttribute;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "color_attribute_id", nullable = false)
    private ProductAttribute colorAttribute;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_attribute_id", nullable = false)
    private ProductAttribute brandAttribute;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public ProductSku() {}

    // Private constructor to enforce immutability
    private ProductSku(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.sizeAttribute = builder.sizeAttribute;
        this.colorAttribute = builder.colorAttribute;
        this.brandAttribute = builder.brandAttribute;
        this.sku = builder.sku;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.createdAt = builder.createdAt;
        this.deletedAt = builder.deletedAt;
    }

    @Override
    public String toString() {
        return "\n ProductSkuService{" +
                "id=" + id +
                ", product=" + product +
                ", sizeAttribute=" + sizeAttribute +
                ", colorAttribute=" + colorAttribute +
                ", brandAttribute=" + brandAttribute +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSku that = (ProductSku) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(product, that.product) &&
                Objects.equals(sizeAttribute, that.sizeAttribute) &&
                Objects.equals(colorAttribute, that.colorAttribute) &&
                Objects.equals(brandAttribute, that.brandAttribute) &&
                Objects.equals(sku, that.sku) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, sizeAttribute, colorAttribute, brandAttribute, sku, price, quantity, createdAt, deletedAt);
    }

    public static class Builder {
        private Long id;
        private Product product;
        private ProductAttribute sizeAttribute;
        private ProductAttribute colorAttribute;
        private ProductAttribute brandAttribute;
        private String sku;
        private Double price;
        private Integer quantity;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setSizeAttribute(ProductAttribute sizeAttribute) {
            this.sizeAttribute = sizeAttribute;
            return this;
        }

        public Builder setColorAttribute(ProductAttribute colorAttribute) {
            this.colorAttribute = colorAttribute;
            return this;
        }

        public Builder setBrandAttribute(ProductAttribute brandAttribute) {
            this.brandAttribute = brandAttribute;
            return this;
        }

        public Builder setSku(String sku) {
            this.sku = sku;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
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

        public Builder copy(ProductSku productSku) {
            this.id = productSku.getId();
            this.product = productSku.getProduct();
            this.sizeAttribute = productSku.getSizeAttribute();
            this.colorAttribute = productSku.getColorAttribute();
            this.brandAttribute = productSku.getBrandAttribute();
            this.sku = productSku.getSku();
            this.price = productSku.getPrice();
            this.quantity = productSku.getQuantity();
            this.createdAt = productSku.getCreatedAt();
            this.deletedAt = productSku.getDeletedAt();
            return this;
        }

        public ProductSku build() {
            return new ProductSku(this);
        }
    }
}
