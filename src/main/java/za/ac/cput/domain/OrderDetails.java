package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an order entry in the system.
 * Each entry is associated with a User, PaymentDetails, and contains multiple OrderItems.
 * <p>
 * This entity class is mapped to the "order_details" table in the database.
 *
 * @author Rethabile Ntsekhe
 * @date 25-Aug-24
 */
@Entity
@Getter
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties({"id", "firstName", "lastName", "email"})
    private User user;

    @OneToOne( cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "payment_id", nullable = false)
    @JsonManagedReference(value = "payment-order")
    private PaymentDetails paymentDetails;

    private Double total;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orderDetails",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderDetails() {}

    private OrderDetails(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.paymentDetails = builder.paymentDetails;
        this.total = builder.total;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.orderItems.addAll(new ArrayList<>(builder.orderItems));
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "\n OrderDetails{" +
                "id=" + id +
                ", user=" + user.getFirstName() + " " + user.getLastName() +
                ", paymentDetails=" + paymentDetails.getStatus() +
                ", total=" + total +
                ", orderItems=" + orderItems +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "}\n";
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
        private List<OrderItem> orderItems = new ArrayList<>();

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

        public Builder setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = new ArrayList<>(orderItems);
            return this;
        }

        public Builder copy(OrderDetails orderDetails) {
            this.id = orderDetails.getId();
            this.user = orderDetails.getUser();
            this.paymentDetails = orderDetails.getPaymentDetails();
            this.total = orderDetails.getTotal();
            this.orderItems = new ArrayList<>(orderDetails.getOrderItems());
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(this);
        }
    }
}
