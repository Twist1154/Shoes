package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an order item entry in the system.
 * This class tracks individual products and their quantities within an order.
 * It is mapped to the "order_item" table in the database.
 *
 * This version stores only the IDs of associated objects.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */
@Entity
@Getter
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_details_id", nullable = false)
    private OrderDetails orderDetails;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "productSku_id", nullable = false)
    private ProductSku productSku;

    private int quantity;

    public OrderItem() {
    }

    // Private constructor to be used by the builder
    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.orderDetails = builder.orderDetails;
        this.product = builder.product;
        this.productSku = builder.productSku;
        this.quantity = builder.quantity;
    }



    @Override
    public String toString() {
        return "\n OrderItem{" +
                "id=" + id +
                ", orderDetails=" + orderDetails +  (orderDetails != null ? orderDetails.getTotal() : 0) +
                ", product=" + product + (product != null ? product.getName() : 0) +
                ", productSku=" + productSku + (productSku != null ? productSku.getSku() : 0) +
                ", quantity=" + quantity +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem that = (OrderItem) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(orderDetails, that.orderDetails) &&
                Objects.equals(product, that.product) &&
                Objects.equals(productSku, that.productSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDetails, product, productSku, quantity);
    }

    public static class Builder {
        private Long id;
        private OrderDetails orderDetails;
        private Product product;
        private ProductSku productSku;
        private int quantity;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setOrderDetails(OrderDetails orderDetails) {
            this.orderDetails = orderDetails;
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

        public Builder copy(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.orderDetails = orderItem.getOrderDetails();
            this.product = orderItem.getProduct();
            this.productSku = orderItem.getProductSku();
            this.quantity = orderItem.getQuantity();
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
