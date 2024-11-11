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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDetailsServiceTest {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    private OrderDetails orderDetails;
    private User user;
    private PaymentDetails testPaymentDetails;

    @BeforeEach
    void setUp() {
        // Set up a user for order details (assuming a valid test user exists)
        user = userService.read(2L);

        // Step 1: Create and persist PaymentDetails first
        testPaymentDetails = PaymentDetailsFactory.createPaymentDetails(
                null,        // id will be auto-generated
                null,        // orderDetails will be set later
                100.0,       // total amount
                "Visa",      // payment method
                "Success",   // payment status
                LocalDateTime.now() // payment date
        );

        // Persist the payment details in the database
        testPaymentDetails = paymentDetailsService.create(testPaymentDetails);

        // Step 2: Create OrderDetails with the persisted PaymentDetails
        orderDetails = OrderDetailsFactory.createOrderDetails(
                null,                        // id will be auto-generated
                user,                        // User associated with the order
                testPaymentDetails,          // Link the created payment details
                100.0                         // Order total amount
        );

        // Persist the order details
        orderDetails = orderDetailsService.create(orderDetails);
    }

    @AfterEach
    void tearDown() {
        // Clean up test data by deleting the created order
        /*if (orderDetails != null && orderDetails.getId() != null) {
            orderDetailsService.delete(orderDetails.getId());
        }
        if (testPaymentDetails != null && testPaymentDetails.getId() != null) {
            paymentDetailsService.delete(testPaymentDetails.getId());
        }*/
    }

    @Test
    @Order(1)
    void create() {
        OrderDetails newOrder = OrderDetailsFactory.createOrderDetails(
                null,
                user,
                testPaymentDetails,
                200.0
        );
        OrderDetails createdOrder = orderDetailsService.create(newOrder);

        // Verify the order was created
        assertNotNull(createdOrder);
        assertNotNull(createdOrder.getId());
        assertEquals(user.getId(), createdOrder.getUser().getId());
        assertEquals(200.0, createdOrder.getTotal());
    }

    @Test
    @Order(2)
    void read() {
        OrderDetails foundOrder = orderDetailsService.read(orderDetails.getId());
        assertNotNull(foundOrder);
        assertEquals(orderDetails.getId(), foundOrder.getId());
    }

    @Test
    @Order(3)
    void update() {
        // Update the order details
        OrderDetails updatedOrder = new OrderDetails.Builder()
                .copy(orderDetails)
                .setTotal(150.0)
                .build();

        OrderDetails result = orderDetailsService.update(updatedOrder);

        // Verify the updated fields
        assertNotNull(result);
        assertEquals(150.0, result.getTotal());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    @Order(4)
    void delete() {
        orderDetailsService.delete(orderDetails.getId());
        OrderDetails deletedOrder = orderDetailsService.read(orderDetails.getId());
        assertNull(deletedOrder); // Verify that the order has been deleted
    }

    @Test
    @Order(5)
    void findAll() {
        List<OrderDetails> orders = orderDetailsService.findAll();
        assertFalse(orders.isEmpty());
    }

    @Test
    @Order(6)
    void findByUserId() {
        List<OrderDetails> orders = orderDetailsService.findByUserId(user.getId());
        assertFalse(orders.isEmpty());
        assertEquals(user.getId(), orders.get(0).getUser().getId());
    }

    @Test
    @Order(7)
    void findByTotalGreaterThanEqual() {
        List<OrderDetails> orders = orderDetailsService.findByTotalGreaterThanEqual(100.0);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(order -> order.getTotal() >= 100.0));
    }

    @Test
    @Order(8)
    void findByCreatedAtAfter() {
        List<OrderDetails> orders = orderDetailsService.findByCreatedAtAfter(LocalDateTime.now().minusDays(1));
        assertFalse(orders.isEmpty());
    }

    @Test
    @Order(9)
    void findByUpdatedAtBefore() {
        List<OrderDetails> orders = orderDetailsService.findByUpdatedAtBefore(LocalDateTime.now().minusDays(5));
        assertTrue(orders.isEmpty());
    }

    @Test
    @Order(10)
    void findByPaymentId() {
        List<OrderDetails> orders = orderDetailsService.findByPaymentId(testPaymentDetails.getId());
        assertFalse(orders.isEmpty());
        assertEquals(testPaymentDetails.getId(), orders.get(0).getPaymentDetails().getId());
    }

    @Test
    @Order(11)
    void findByUserIdOrderByCreatedAtDesc() {
        List<OrderDetails> orders = orderDetailsService.findByUserIdOrderByCreatedAtDesc(user.getId());
        System.out.println(orders);
        assertFalse(orders.isEmpty());
    }
}
