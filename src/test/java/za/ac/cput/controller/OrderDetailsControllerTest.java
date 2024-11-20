package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.service.OrderDetailsService;
import za.ac.cput.service.PaymentDetailsService;
import za.ac.cput.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDetailsControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentDetailsService paymentDetailsService;

    private final String baseUrl = "http://localhost:8080/api/order-details";

    private OrderDetails orderDetails;
    private User user;
    private PaymentDetails paymentDetails;

    @BeforeEach
    void setUp() {
        user = userService.read(2L);
        paymentDetails = paymentDetailsService.read(2L);

        if (paymentDetails == null) {
            paymentDetails = paymentDetailsService.read(3L);
        }

        orderDetails = OrderDetailsFactory.createOrderDetails(
                null,
                user,
                paymentDetails,
                1500.0
        );

        ResponseEntity<OrderDetails> response = restTemplate.postForEntity(baseUrl, orderDetails, OrderDetails.class);
        orderDetails = response.getBody();
        assertNotNull(orderDetails);
        assertNotNull(orderDetails.getId());
    }

    @AfterEach
    void tearDown() {
        if (orderDetails != null && orderDetails.getId() != null) {
            restTemplate.delete(baseUrl + "/" + orderDetails.getId());
        }
    }

    @Test
    @Order(1)
    void createOrderDetails() {
        OrderDetails newOrderDetails = OrderDetailsFactory.createOrderDetails(
                null,
                user,
                paymentDetails,
                2000.0
        );

        ResponseEntity<OrderDetails> response = restTemplate.postForEntity(baseUrl, newOrderDetails, OrderDetails.class);
        OrderDetails createdOrderDetails = response.getBody();

        assertNotNull(createdOrderDetails);
        assertNotNull(createdOrderDetails.getId());
        assertEquals(2000.0, createdOrderDetails.getTotal());
    }

    @Test
    @Order(2)
    void getOrderDetailsById() {
        ResponseEntity<OrderDetails> response = restTemplate.getForEntity(baseUrl + "/" + orderDetails.getId(), OrderDetails.class);
        assertEquals(orderDetails.getId(), response.getBody().getId());
    }

    @Test
    @Order(3)
    void updateOrderDetails() {
        OrderDetails updatedOrderDetails = new OrderDetails.Builder()
                .copy(orderDetails)
                .setTotal(2500.0)
                .build();

        restTemplate.put(baseUrl + "/" + orderDetails.getId(), updatedOrderDetails);

        ResponseEntity<OrderDetails> response = restTemplate.getForEntity(baseUrl + "/" + orderDetails.getId(), OrderDetails.class);
        assertEquals(2500.0, response.getBody().getTotal());
    }

    @Test
    @Order(4)
    void deleteOrderDetails() {
        restTemplate.delete(baseUrl + "/" + orderDetails.getId());
        ResponseEntity<OrderDetails> response = restTemplate.getForEntity(baseUrl + "/" + orderDetails.getId(), OrderDetails.class);
        assertEquals(404, response.getStatusCode()); // Assuming 404 for not found
    }

    @Test
    @Order(5)
    void getAllOrderDetails() {
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl, OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertFalse(orderDetailsList.isEmpty());
    }

    @Test
    @Order(6)
    void getOrderDetailsByUserId() {
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl + "/user/" + user.getId(), OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertTrue(orderDetailsList.stream().anyMatch(od -> od.getUser().getId().equals(user.getId())));
    }

    @Test
    @Order(7)
    void getOrderDetailsByTotalGreaterThanEqual() {
        double minTotal = 1500.0;
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl + "/total-gte/" + minTotal, OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertTrue(orderDetailsList.stream().allMatch(od -> od.getTotal() >= minTotal));
    }

    @Test
    @Order(8)
    void getOrderDetailsByCreatedAtAfter() {
        LocalDateTime afterDate = LocalDateTime.now().minusDays(1);
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl + "/created-after/" + afterDate, OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertTrue(orderDetailsList.stream().allMatch(od -> od.getCreatedAt().isAfter(afterDate)));
    }

    @Test
    @Order(9)
    void getOrderDetailsByUpdatedAtBefore() {
        LocalDateTime beforeDate = LocalDateTime.now().plusDays(1);
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl + "/updated-before/" + beforeDate, OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertTrue(orderDetailsList.stream().allMatch(od -> od.getUpdatedAt() == null || od.getUpdatedAt().isBefore(beforeDate)));
    }

    @Test
    @Order(10)
    void getOrderDetailsByPaymentId() {
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl + "/payment/" + paymentDetails.getId(), OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertTrue(orderDetailsList.stream().allMatch(od -> od.getPaymentDetails().getId().equals(paymentDetails.getId())));
    }

    @Test
    @Order(11)
    void getOrderDetailsByUserIdOrderByCreatedAtDesc() {
        ResponseEntity<OrderDetails[]> response = restTemplate.getForEntity(baseUrl + "/user/" + user.getId() + "/created-desc", OrderDetails[].class);
        List<OrderDetails> orderDetailsList = List.of(response.getBody());
        assertNotNull(orderDetailsList);
        assertTrue(orderDetailsList.size() > 1);
        for (int i = 1; i < orderDetailsList.size(); i++) {
            assertTrue(orderDetailsList.get(i - 1).getCreatedAt().isAfter(orderDetailsList.get(i).getCreatedAt()));
        }
    }
}
