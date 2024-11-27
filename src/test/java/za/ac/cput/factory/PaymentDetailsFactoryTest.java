package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentDetailsFactoryTest {

    private PaymentDetails paymentDetails;
    private OrderDetails orderDetails;
    private User user;
    private Set<Role> roles;

    @BeforeEach
    void setup() {
        // Initialize valid objects for testing
        roles = new HashSet<>(Set.of(Role.USER, Role.ADMIN));

        // Set up a sample User object using the factory method
        user = UserFactory.createUser(
                1L,
                "avatar.jpg",
                "John",
                "Doe",
                "user1",
                "johndoe@example.com",
                LocalDate.parse("1990-01-01"),
                roles,
                "0123456789",
                "password123");

        // Set up a sample PaymentDetails object using the factory method
        paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                1L,
                100.0,
                "PayPal",
                "Success"
        );
        // Set up a sample OrderDetails object
        orderDetails = OrderDetailsFactory.createOrderDetails(
                1L,
                user,
                paymentDetails,
                100.0
        );
    }

    @Test
    void testCreatePaymentDetails() {
        // Verify that the PaymentDetails object is not null
        assertNotNull(paymentDetails);

        // Print the created PaymentDetails object to the terminal
        System.out.println("Created PaymentDetails: " + paymentDetails);
    }

    @Test
    void testCreatePaymentDetails_WithNullAmount_ThrowsIllegalArgumentException() {
        // Try to create a PaymentDetails object with null amount
        assertThrows(IllegalArgumentException.class,
                () -> PaymentDetailsFactory.createPaymentDetails(
                        1L,
                        null,
                        "PayPal",
                        "Success"
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating PaymentDetails with null amount");
    }

    @Test
    void testCreatePaymentDetails_WithNullProvider_ThrowsIllegalArgumentException() {
        // Try to create a PaymentDetails object with null provider
        assertThrows(IllegalArgumentException.class,
                () -> PaymentDetailsFactory.createPaymentDetails(
                        1L,
                        100.0,
                        null,
                        "Success"
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating PaymentDetails with null provider");
    }

    @Test
    void testCreatePaymentDetails_WithNullStatus_ThrowsIllegalArgumentException() {
        // Try to create a PaymentDetails object with null status
        assertThrows(IllegalArgumentException.class,
                () -> PaymentDetailsFactory.createPaymentDetails(
                        1L,
                        100.0,
                        "PayPal",
                        null
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating PaymentDetails with null status");
    }
}