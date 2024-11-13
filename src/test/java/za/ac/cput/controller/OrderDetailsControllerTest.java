package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.service.OrderDetailsService;
import za.ac.cput.service.PaymentDetailsService;
import za.ac.cput.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDetailsControllerTest {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentDetailsService paymentDetailsService;
     @Autowired
    private TestRestTemplate restTemplate;


    private final String baseUrl ="http://localhost:8080/api/order-details";

    private OrderDetails orderDetails;
    private User user;
    private PaymentDetails paymentDetails;
    @BeforeEach
    void setUp() {
        user = userService.read(2L);
        paymentDetails = paymentDetailsService.read(2L);

        if(paymentDetails == null) {
            paymentDetails = paymentDetailsService.read(3L);
            System.out.println("Payment Details: with ID 1 was empty");
            System.out.println("Payment Details: " + paymentDetails);
            //paymentDetails = paymentDetailsService.create(paymentDetails);
        }


        orderDetails = OrderDetailsFactory.createOrderDetails(
                null,
                user,
                paymentDetails,
                1500.0
        );


    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createOrderDetails() {
    }

    @Test
    void getOrderDetailsById() {
    }

    @Test
    void updateOrderDetails() {
    }

    @Test
    void deleteOrderDetails() {
    }

    @Test
    void getAllOrderDetails() {
    }

    @Test
    void getOrderDetailsByUserId() {
    }

    @Test
    void getOrderDetailsByTotalGreaterThanEqual() {
    }

    @Test
    void getOrderDetailsByCreatedAtAfter() {
    }

    @Test
    void getOrderDetailsByUpdatedAtBefore() {
    }

    @Test
    void getOrderDetailsByPaymentId() {
    }

    @Test
    void getOrderDetailsByUserIdOrderByCreatedAtDesc() {
    }
}