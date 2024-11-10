package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an order details entry in the system.
 * Each entry provides details about an order, including the user_id who placed the order,
 * payment details, and the total amount. This entity is mapped to the "order_details" table in the database.
 * <p>
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@Entity
@Getter
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentDetails paymentDetails;

    private Double total;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public OrderDetails() {
    }

    private OrderDetails(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.paymentDetails = builder.paymentDetails;
        this.total = builder.total;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    @Override
    public String toString() {
        return "\n OrderDetails{" +
                "id=" + id +
                ", user=" + user.getFirstName() + user.getLastName() +
                ", paymentDetails=" + paymentDetails.getStatus() +
                ", total=" + total +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(paymentDetails, that.paymentDetails) &&
                Objects.equals(total, that.total) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, paymentDetails, total, createdAt, updatedAt);
    }

    public static class Builder {
        private Long id;
        private User user;
        private PaymentDetails paymentDetails;
        private Double total;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setPaymentDetails(PaymentDetails paymentDetails) {
            this.paymentDetails = paymentDetails;
            return this;
        }

        public Builder setTotal(Double total) {
            this.total = total;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder copy(OrderDetails orderDetails) {
            this.id = orderDetails.getId();
            this.user = orderDetails.getUser();
            this.paymentDetails = orderDetails.getPaymentDetails();
            this.total = orderDetails.getTotal();
            this.createdAt = orderDetails.getCreatedAt();
            this.updatedAt = orderDetails.getUpdatedAt();
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(this);
        }
    }
}
