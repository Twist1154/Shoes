package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a cart item entry in the system.
 * Each entry is associated with a Cart, a Product, and a ProductSkuService.
 *
 * This entity class is mapped to the "cart_item" table in the database.
 * Includes the necessary mappings for relationships to other entities.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */
@Entity
@Getter
@Table(name = "cart_item")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @ManyToOne
    @JoinColumn(name = "products_sku_id", nullable = false)
    private ProductSku productSku;

    @Column(nullable = false)
    private int quantity;

    public CartItem() {}

    private CartItem(Builder builder) {
        this.id = builder.id;
        this.cart = builder.cart;
        this.product = builder.product;
        this.productSku = builder.productSku;
        this.quantity = builder.quantity;
    }

    @Override
    public String toString() {
        return "\n CartItem{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product.getName() +'\''+
                ", productSku=" + productSku.getSku()+'\'' +
                ", quantity=" + quantity +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity &&
                Objects.equals(id, cartItem.id) &&
                Objects.equals(cart, cartItem.cart) &&
                Objects.equals(product, cartItem.product) &&
                Objects.equals(productSku, cartItem.productSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, product, productSku, quantity);
    }

    public static class Builder {
        private Long id;
        private Cart cart;
        private Product product;
        private ProductSku productSku;
        private int quantity;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setProductSku(ProductSku productSku) {
            this.productSku = productSku;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder copy(CartItem cartItem) {
            this.id = cartItem.getId();
            this.cart = cartItem.getCart();
            this.product = cartItem.getProduct();
            this.productSku = cartItem.getProductSku();
            this.quantity = cartItem.getQuantity();
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
