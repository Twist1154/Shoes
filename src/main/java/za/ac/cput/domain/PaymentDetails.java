package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents payment details within the system.
 * This class is mapped to the "payment_details" table in the database.
 *
 * Stores only the ID of the associated OrderDetails entity.
 */
@Entity
@Getter
@Table(name = "payment_details")
public class PaymentDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String provider;
    private String status;

    @OneToOne(mappedBy = "paymentDetails", fetch = FetchType.EAGER)
    @JoinColumn
    @JsonBackReference(value = "payment-order")

    private OrderDetails orderDetails;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public PaymentDetails() {}

    private PaymentDetails(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.provider = builder.provider;
        this.status = builder.status;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "\n PaymentDetails{" +
                "id=" + id +
                ", amount=" + amount +
                ", provider='" + provider + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDetails that = (PaymentDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, provider, status, createdAt);
    }

    public static class Builder {
        private Long id;
        private Double amount;
        private String provider;
        private String status;
        private LocalDateTime createdAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setProvider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(PaymentDetails paymentDetails) {
            this.id = paymentDetails.getId();
            this.amount = paymentDetails.getAmount();
            this.provider = paymentDetails.getProvider();
            this.status = paymentDetails.getStatus();
            return this;
        }

        public PaymentDetails build() {
            return new PaymentDetails(this);
        }
    }
}
