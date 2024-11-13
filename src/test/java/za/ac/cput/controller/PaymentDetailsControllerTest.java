package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.service.PaymentDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentDetailsControllerTest {

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @Autowired
    private TestRestTemplate restTemplate;

    private PaymentDetails paymentDetails;

    private OrderDetails orderDetails;
    private User user;
    private final String baseUrl = "/payment-details";

    @BeforeEach
    void setUp() {
         orderDetails =new OrderDetails.Builder().build();
        // Create initial payment details
        paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                null, // ID will be auto-generated
                1000.00, // Amount
                "PayPal", // Provider
                "Paid"
        );
        // Post initial payment details to create it in the database
        restTemplate.postForEntity(baseUrl, paymentDetails, PaymentDetails.class);
    }

    @Test
    @Order(1)
    void createPaymentDetails() {
        PaymentDetails newPaymentDetails = PaymentDetailsFactory.createPaymentDetails(
                null,
                1500.00,
                "Credit Card",
                "Pending"
        );
        ResponseEntity<PaymentDetails> response = restTemplate.postForEntity(baseUrl, newPaymentDetails, PaymentDetails.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    @Order(2)
    void getPaymentDetailsById() {
        ResponseEntity<PaymentDetails> response = restTemplate.getForEntity(baseUrl + "/1", PaymentDetails.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(paymentDetails.getId(), response.getBody().getId());
    }

    @Test
    @Order(3)
    void updatePaymentDetails() {
        paymentDetails =new PaymentDetails();

        PaymentDetails updatedpayment = new PaymentDetails.Builder()
                .copy(paymentDetails)
                .setAmount(2000.00)
                .build();

        ResponseEntity<PaymentDetails> response = restTemplate.exchange(
                baseUrl + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updatedpayment),
                PaymentDetails.class,
                paymentDetails.getId()
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2000.00, response.getBody().getAmount());
    }

    @Test
    @Order(4)
    void deletePaymentDetails() {
        restTemplate.delete(baseUrl + "/{id}", paymentDetails.getId());
        ResponseEntity<PaymentDetails> response = restTemplate.getForEntity(baseUrl + "/{id}", PaymentDetails.class, paymentDetails.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    void getAllPaymentDetails() {
        ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void getPaymentDetailsByProvider() {
        ResponseEntity<List> response = restTemplate.exchange(
                baseUrl + "/provider/{provider}",
                HttpMethod.GET,
                null,
                List.class,
                "PayPal"
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
        assertEquals("PayPal", ((PaymentDetails) response.getBody().get(0)).getProvider());
    }

    @Test
    void getPaymentDetailsByStatus() {
        ResponseEntity<List> response = restTemplate.exchange(
                baseUrl + "/status/{status}",
                HttpMethod.GET,
                null,
                List.class,
                "Paid"
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void getPaymentDetailsCreatedAfter() {
        ResponseEntity<List> response = restTemplate.exchange(
                baseUrl + "/created-after?createdAt={createdAt}",
                HttpMethod.GET,
                null,
                List.class,
                LocalDate.now().minusDays(1)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void getPaymentDetailsCreatedBetween() {
        ResponseEntity<List> response = restTemplate.exchange(
                baseUrl + "/created-between?startDate={startDate}&endDate={endDate}",
                HttpMethod.GET,
                null,
                List.class,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void countPaymentDetailsByStatus() {
        ResponseEntity<Long> response = restTemplate.exchange(
                baseUrl + "/count-by-status?status={status}",
                HttpMethod.GET,
                null,
                Long.class,
                "Paid"
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() > 0);
    }

    @Test
    void deletePaymentDetailsByOrderDetailsId() {
        ResponseEntity<Integer> response = restTemplate.exchange(
                baseUrl + "/order-details/{orderDetailsId}",
                HttpMethod.DELETE,
                null,
                Integer.class,
                1L
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() > 0);
    }
}
