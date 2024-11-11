package za.ac.cput.factory;

import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

/**
 * Factory class for creating instances of {@link PaymentDetails}.
 * Provides static methods to create {@link PaymentDetails} objects from various inputs.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
public class PaymentDetailsFactory {

    /**
     * Creates a {@link PaymentDetails} instance from various inputs.
     *
     * @param id          the ID of the payment details (nullable)
     * @param amount      the amount of the payment (cannot be null or empty)
     * @param provider    the payment provider (cannot be null or empty)
     * @param status      the status of the payment (cannot be null or empty)
     * @param createdAt   the date the payment was created (cannot be null)
     * @return a new {@link PaymentDetails} object with properties set from the input parameters
     * @throws IllegalArgumentException if any required parameters are null or invalid
     */
    public static PaymentDetails createPaymentDetails(Long id,
                                                      Double amount,
                                                      String provider,
                                                      String status,
                                                      LocalDateTime createdAt) {
        // Define constants for the switch cases
        final int AMOUNT_NULL = 1;
        final int PROVIDER_NULL = 2;
        final int STATUS_NULL = 4;
        final int CREATED_AT_NULL = 8;

        // Calculate the errorFlags based on null or empty checks
        int errorFlags = 0;

        // Check for null or empty values for required parameters
        if (Helper.isDoubleNullOrEmpty(amount)) {
            errorFlags |= AMOUNT_NULL;
        }
        if (Helper.isNullOrEmpty(provider)) {
            errorFlags |= PROVIDER_NULL;
        }
        if (Helper.isNullOrEmpty(status)) {
            errorFlags |= STATUS_NULL;
        }
        if (Helper.isNullOrEmpty(createdAt)) {
            errorFlags |= CREATED_AT_NULL;
        }

        // Use switch statement to throw exception based on the flags
        switch (errorFlags) {
            case AMOUNT_NULL | PROVIDER_NULL | STATUS_NULL | CREATED_AT_NULL:
                throw new IllegalArgumentException("Amount, provider, status, and created date cannot be null or empty");
            case AMOUNT_NULL | PROVIDER_NULL | STATUS_NULL:
                throw new IllegalArgumentException("Amount, provider, and status cannot be null or empty");
            case AMOUNT_NULL | PROVIDER_NULL:
                throw new IllegalArgumentException("Amount and provider cannot be null or empty");
            case AMOUNT_NULL | STATUS_NULL:
                throw new IllegalArgumentException("Amount and status cannot be null or empty");
            case PROVIDER_NULL | STATUS_NULL:
                throw new IllegalArgumentException("Provider and status cannot be null or empty");
            case AMOUNT_NULL:
                throw new IllegalArgumentException("Amount cannot be null or empty");
            case PROVIDER_NULL:
                throw new IllegalArgumentException("Provider cannot be null or empty");
            case STATUS_NULL:
                throw new IllegalArgumentException("Status cannot be null or empty");
            case CREATED_AT_NULL:
                throw new IllegalArgumentException("Created date cannot be null");
            default:
                // No null or empty values
                break;
        }

        // Use the Builder pattern to create a new PaymentDetails object
        return new PaymentDetails.Builder()
                .setId(id) // Set the ID of the payment details (nullable)
                .setAmount(amount) // Set the amount of the payment (required)
                .setProvider(provider) // Set the payment provider (required)
                .setStatus(status) // Set the status of the payment (required)
                .setCreatedAt(createdAt) // Set the date the payment was created (required)
                .build();
    }
}
