package za.ac.cput.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.repository.PaymentDetailsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_CLASS)
class PaymentDetailsServiceTest {

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    private PaymentDetails paymentDetails;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {

        // Create and save paymentDetails for shared test use
        PaymentDetails paymentDetails1 = PaymentDetailsFactory.createPaymentDetails(
                1L,
                1000.00,
                "PayPal",
                "Paid"
        );

        paymentDetails = paymentDetailsService.create(paymentDetails1);

    }

    @AfterEach
    void tearDown() {
         paymentDetailsRepository.deleteById(paymentDetails.getId());
    }

    @Test
    @Order(1)
    void create() {

        PaymentDetails created = paymentDetailsService.create(paymentDetails);

        // Print out the created payment details
        System.out.println("Created PaymentDetails: " + created);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("PayPal", created.getProvider());
    }

    @Test
    @Order(2)
    void read() {
        paymentDetailsService.create(paymentDetails);
        PaymentDetails readPaymentDetails = paymentDetailsService.read(paymentDetails.getId());

        // Print out the read payment details
        System.out.println("Read PaymentDetails: " + readPaymentDetails);

        assertNotNull(readPaymentDetails);
        assertEquals(paymentDetails.getId(), readPaymentDetails.getId());
    }

    @Test
    @Order(3)
    void update() {
        PaymentDetails updator =paymentDetailsService.create(paymentDetails);
        paymentDetails = new PaymentDetails.Builder()
                .copy(updator)
                .setProvider("PayPal")
                .setAmount(2500.00)
                .setStatus("Cancelled")
                .build();

        PaymentDetails updatedPaymentDetails = paymentDetailsService.update(paymentDetails);

        // Print out the updated payment details
        System.out.println("Updated PaymentDetails: " + updatedPaymentDetails);

        assertNotNull(updatedPaymentDetails);
        assertEquals(2500.00, updatedPaymentDetails.getAmount());
    }

    @Test
    @Order(4)
    void delete() {
        PaymentDetails tempPaymentDetails = paymentDetailsService.create(
                PaymentDetailsFactory.createPaymentDetails(
                        null,
                        2000.00,
                        "Bank Transfer",
                        "Completed")
        );

        // Print out the temporary payment details for deletion
        System.out.println("Temp PaymentDetails for deletion: " + tempPaymentDetails);

        boolean deleted = paymentDetailsService.delete(tempPaymentDetails.getId());
        assertTrue(deleted && paymentDetailsService.read(tempPaymentDetails.getId()) == null);
    }

    @Test
    @Order(5)
    void findAll() {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findAll();

        // Print out the list of all payment details
        System.out.println("All PaymentDetails: " + paymentDetailsList);

        assertFalse(paymentDetailsList.isEmpty());
    }

    @Test
    @Order(6)
    void findByProvider() {
        PaymentDetails testPayment = new PaymentDetails.Builder()
                .copy(paymentDetails)
                .setProvider("PayPal")
                .build();

        paymentDetailsService.create(testPayment);

        List<PaymentDetails> paymentsByProvider = paymentDetailsService.findByProvider("PayPal");

        // Print out the payment details found by provider
        System.out.println("PaymentDetails by Provider 'PayPal': " + paymentsByProvider);

        assertFalse(paymentsByProvider.isEmpty());
        assertEquals("PayPal", paymentsByProvider.get(0).getProvider());
    }

    @Test
    @Order(7)
    void findByStatus() {
        List<PaymentDetails> paymentsByStatus = paymentDetailsService.findByStatus("Paid");

        // Print out the payment details found by status
        System.out.println("PaymentDetails by Status 'Paid': " + paymentsByStatus);

        assertFalse(paymentsByStatus.isEmpty());
        assertEquals("Paid", paymentsByStatus.get(0).getStatus());
    }

    @Test
    @Order(8)
    void findByCreatedAtAfter() {
        List<PaymentDetails> paymentsAfterDate = paymentDetailsService.findByCreatedAtAfter(LocalDateTime.now().minusDays(1));

        // Print out the payment details found by createdAt after a date
        System.out.println("PaymentDetails created after date: " + paymentsAfterDate);

        assertFalse(paymentsAfterDate.isEmpty());
    }

    @Test
    @Order(9)
    void findByCreatedAtBetween() {
        List<PaymentDetails> paymentsBetweenDates = paymentDetailsService.findByCreatedAtBetween(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        );

        // Print out the payment details found by createdAt between dates
        System.out.println("PaymentDetails created between dates: " + paymentsBetweenDates);

        assertFalse(paymentsBetweenDates.isEmpty());
    }

    @Test
    @Order(10)
    void countByStatus() {
        long count = paymentDetailsService.countByStatus("Paid");

        // Print out the count of payment details by status
        System.out.println("Count of PaymentDetails by Status 'Paid': " + count);

        assertTrue(count > 0);
    }
}
