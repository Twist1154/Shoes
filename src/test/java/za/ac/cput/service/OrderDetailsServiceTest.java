package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.Application;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class OrderDetailsServiceTest {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    private OrderDetails orderDetails;
    private User user;
    private PaymentDetails paymentDetails;

    @BeforeEach
    void setUp() {
        // Ensure the user exists or create a new one
        user = userService.read(2L); // Assuming user with ID 2 exists, otherwise create
        if (user == null) {
            String Username = "User" + System.currentTimeMillis();
            String email = "User" + System.currentTimeMillis()+ "@example.com";
            user = UserFactory.createUser(
                    null,
                    "avatar.jpg",
                    "John",
                    "Doe",
                    Username,
                    email,
                    LocalDate.parse("1990-01-01"),
                    Set.of(Role.USER, Role.ADMIN),
                    "0123456789",
                    "password123"
            );
            user = userService.create(user);
            System.out.println("User created with ID: " + user.getId());
        }

        // Ensure payment details are created and saved
        if (paymentDetails == null) {
            paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                    null, // ID will be generated upon save
                    120.0,
                    "CreditCard",
                    "Completed"
            );
            paymentDetails = paymentDetailsService.create(paymentDetails); // Persist payment details
            System.out.println("Payment Details created with ID: " + paymentDetails.getId());
        }

        // Create order details using the newly created or retrieved paymentDetails and user
        orderDetails = OrderDetailsFactory.createOrderDetails(
                null, // ID will be generated upon save
                user,
                paymentDetails,
                1500.0
        );

        // Persist the order details
        orderDetails = orderDetailsService.create(orderDetails);
        System.out.println("OrderDetails created with ID: " + orderDetails.getId());
    }

    @AfterEach
    void tearDown() {
        // Clean up test data by deleting the created order, except for the order with ID 1
        if (orderDetails != null && orderDetails.getId() != null && orderDetails.getId() > 2) {
            orderDetailsService.delete(orderDetails.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        // Create order details again to verify persistence
        OrderDetails createdOrder = orderDetailsService.create(orderDetails);

        // Verify the order was created
        assertNotNull(createdOrder);
        assertNotNull(createdOrder.getId());
        assertEquals(user.getId(), createdOrder.getUser().getId());
        assertEquals(1500.0, createdOrder.getTotal());
    }

    @Test
    @Order(2)
    void read() {
        // Read the saved order details by ID
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
        // Delete the created order details
        orderDetailsService.delete(orderDetails.getId());
        OrderDetails deletedOrder = orderDetailsService.read(orderDetails.getId());
        assertNull(deletedOrder); // Verify that the order has been deleted
    }

    @Test
    @Order(5)
    void findAll() {
        // Retrieve all order details
        List<OrderDetails> orders = orderDetailsService.findAll();
        assertFalse(orders.isEmpty());
    }

    @Test
    @Order(6)
    void findByUserId() {
        // Find order details by user ID
        List<OrderDetails> orders = orderDetailsService.findByUserId(user.getId());
        assertFalse(orders.isEmpty());
        assertEquals(user.getId(), orders.get(0).getUser().getId());
    }

    @Test
    @Order(7)
    void findByTotalGreaterThanEqual() {
        // Find order details by total greater than or equal to a specified amount
        List<OrderDetails> orders = orderDetailsService.findByTotalGreaterThanEqual(100.0);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(order -> order.getTotal() >= 100.0));
    }

    @Test
    @Order(8)
    void findByCreatedAtAfter() {
        // Find order details that were created after a specific timestamp
        List<OrderDetails> orders = orderDetailsService.findByCreatedAtAfter(LocalDateTime.now().minusDays(1));
        assertFalse(orders.isEmpty());
    }

    @Test
    @Order(9)
    void findByUpdatedAtBefore() {
        // Find order details that were updated before a specific timestamp
        List<OrderDetails> orders = orderDetailsService.findByUpdatedAtBefore(LocalDateTime.now().minusDays(5));
        assertTrue(orders.isEmpty());
    }

    @Test
    @Order(10)
    void findByPaymentId() {
        // Find order details by payment ID
        List<OrderDetails> orders = orderDetailsService.findByPaymentId(paymentDetails.getId());
        assertFalse(orders.isEmpty());
        assertEquals(paymentDetails.getId(), orders.get(0).getPaymentDetails().getId());
    }

    @Test
    @Order(11)
    void findByUserIdOrderByCreatedAtDesc() {
        // Find order details by user ID, ordered by creation date descending
        List<OrderDetails> orders = orderDetailsService.findByUserIdOrderByCreatedAtDesc(user.getId());
        System.out.println(orders);
        assertFalse(orders.isEmpty());
    }
}
