package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.Application;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.repository.PaymentDetailsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PaymentDetailsServiceTest {

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;
    private PaymentDetails paymentDetails;

    private OrderDetails orderDetails;

    @Autowired
    private OrderDetailsService orderDetailsService;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User(); // Initialize a sample User object
        // Set up a sample OrderDetails object using the factory method
        orderDetails = OrderDetailsFactory.createOrderDetails(
                1L,
                user,
                paymentDetails,
                100.0
        );
        System.out.println("Order Details: " + orderDetails);
        paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                null,
                1000.00,
                "PayPal",
                "Paid",
                LocalDateTime.now()
        );
        System.out.println("created payment details: "+paymentDetails);
        paymentDetailsService.create(paymentDetails);
    }

    @AfterEach
    void tearDown() {
        //paymentDetailsRepository.deleteAll();
    }

    @Test
    @Order(1)
    void create() {
        PaymentDetails newPaymentDetails = PaymentDetailsFactory.createPaymentDetails(
                null,
                1500.00,
                "Credit Card",
                "Pending",
                LocalDateTime.now()
        );
        PaymentDetails created = paymentDetailsService.create(newPaymentDetails);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Credit Card", created.getProvider());
    }

    @Test
    @Order(2)
    void read() {
        PaymentDetails readPaymentDetails = paymentDetailsService.read(paymentDetails.getId());
        assertNotNull(readPaymentDetails);
        assertEquals(paymentDetails.getId(), readPaymentDetails.getId());
    }

    @Test
    @Order(3)
    void update() {
        paymentDetails = new PaymentDetails.Builder()
                .copy(paymentDetails)
                .setId(paymentDetails.getId())
                .setProvider("PayPal")
                .setAmount(2000.00)
                .build();
        PaymentDetails updatedPaymentDetails = paymentDetailsService.update(paymentDetails);
        System.out.println("----------------------------------------------------------------");
        System.out.println(updatedPaymentDetails);
        assertNotNull(updatedPaymentDetails);
        assertEquals(2000.00, updatedPaymentDetails.getAmount());
    }

    @Test
    @Order(4)
    void delete() {
        paymentDetailsService.delete(paymentDetails.getId());
        PaymentDetails deletedPaymentDetails = paymentDetailsService.read(paymentDetails.getId());
        assertNull(deletedPaymentDetails);
    }

    @Test
    @Order(5)
    void findAll() {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findAll();
        assertFalse(paymentDetailsList.isEmpty());
    }

    @Test
    @Order(6)
    void findByProvider() {
        // Setup test data (optional, if not already handled)
        PaymentDetails testPayment = new PaymentDetails.Builder()
                .copy(paymentDetails)
                .setId(null)
                .setProvider("PayPal")
                .build();

        paymentDetailsService.create(testPayment); // Assuming you have a create method in your service

        // Call the method to test
        List<PaymentDetails> paymentsByProvider = paymentDetailsService.findByProvider("PayPal");

        // Debugging output (optional)
        System.out.println("------------------------------------------------------------------");
        System.out.println(paymentsByProvider);

        // Assertions
        assertFalse(paymentsByProvider.isEmpty(), "Payment list should not be empty"); // Check that the list is not empty
        assertEquals("PayPal", paymentsByProvider.get(0).getProvider(), "Provider should be 'PayPal'"); // Ensure the first payment's provider is "PayPal"
    }


    @Test
    @Order(7)
    void findByStatus() {
        List<PaymentDetails> paymentsByStatus = paymentDetailsService.findByStatus("Paid");
        assertFalse(paymentsByStatus.isEmpty());
        assertEquals("Paid", paymentsByStatus.get(0).getStatus());
    }

    @Test
    @Order(10)
    void findByCreatedAtAfter() {
        List<PaymentDetails> paymentsAfterDate = paymentDetailsService.findByCreatedAtAfter(LocalDateTime.now().minusDays(1));
        assertFalse(paymentsAfterDate.isEmpty());
    }

    @Test
    @Order(11)
    void findByCreatedAtBetween() {
        List<PaymentDetails> paymentsBetweenDates = paymentDetailsService.findByCreatedAtBetween(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        );
        assertFalse(paymentsBetweenDates.isEmpty());
    }

    @Test
    @Order(8)
    void countByStatus() {
        long count = paymentDetailsService.countByStatus("Paid");
        assertTrue(count > 0);
    }

    @Test
    @Order(9)
    void deleteByOrderDetailsId() {
        int deletedCount = paymentDetailsService.deleteByOrderDetailsId(1L);
        assertTrue(deletedCount > 0);
    }
}
