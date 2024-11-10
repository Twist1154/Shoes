package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentDetailsFactoryTest {

    private PaymentDetails paymentDetails;
    private OrderDetails orderDetails;

    @BeforeEach
    void setup() {
        // Set up a sample OrderDetails object
        orderDetails = new OrderDetails(); // Initialize a sample OrderDetails object

        // Set up a sample PaymentDetails object using the factory method
        paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                1L,
                orderDetails,
                100.0,
                "PayPal",
                "Success",
                LocalDateTime.parse("2024-06-12T12:00:00")
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
    void testCreatePaymentDetails_WithNullOrderDetails_ThrowsIllegalArgumentException() {
        // Try to create a PaymentDetails object with null OrderDetails
        assertThrows(IllegalArgumentException.class,
                () -> PaymentDetailsFactory.createPaymentDetails(
                        1L,
                        null,
                        100.0,
                        "PayPal",
                        "Success",
                        LocalDateTime.parse("2024-06-12T12:00:00")
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating PaymentDetails with null OrderDetails");
    }

    @Test
    void testCreatePaymentDetails_WithNullAmount_ThrowsIllegalArgumentException() {
        // Try to create a PaymentDetails object with null amount
        assertThrows(IllegalArgumentException.class,
                () -> PaymentDetailsFactory.createPaymentDetails(
                        1L,
                        orderDetails,
                        null,
                        "PayPal",
                        "Success",
                        LocalDateTime.parse("2024-06-12T12:00:00")
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
                        orderDetails,
                        100.0,
                        null,
                        "Success",
                        LocalDateTime.parse("2024-06-12T12:00:00")
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
                        orderDetails,
                        100.0,
                        "PayPal",
                        null,
                        LocalDateTime.parse("2024-06-12T12:00:00")
                )
        );

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating PaymentDetails with null status");
    }
}