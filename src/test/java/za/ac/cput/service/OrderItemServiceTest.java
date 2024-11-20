package za.ac.cput.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.factory.OrderItemFactory;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.OrderItemRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_CLASS)
@Transactional
class OrderItemServiceTest {

    @Autowired
    private OrderItemRepository itemRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentDetailsService paymentDetailsService;

    private OrderItem orderItem;
    private ProductSku productSku;
    private OrderDetails orderDetails;
    private User user;
    private PaymentDetails paymentDetails;

    @BeforeEach
    void setUp() {
        // Ensure Product and ProductSku are retrieved from the database
        Product product = productService.read(1L);
        productSku = productSkuService.read(1L);

        // Create User if not already created
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

        // Create PaymentDetails if not already created
        if (paymentDetails == null) {
            paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                    null,
                    120.0,
                    "CreditCard",
                    "Completed"
            );
            paymentDetails = paymentDetailsService.create(paymentDetails);
            System.out.println("Payment Details created with ID: " + paymentDetails.getId());
        }

        // Create OrderDetails using the newly created or retrieved paymentDetails
        orderDetails = OrderDetailsFactory.createOrderDetails(
                null, // ID will be generated upon save
                user,
                paymentDetails,
                1500.0
        );
        orderDetails = orderDetailsService.create(orderDetails);
        System.out.println("OrderDetails created with ID: " + orderDetails.getId());

        // Set up OrderItem using the created OrderDetails and ProductSku
        OrderItem createOrderItem = OrderItemFactory.createOrderItem(
                null,
                orderDetails,
                product,
                productSku,
                2
        );
         orderItem = orderItemService.create(createOrderItem);

    }

    @AfterEach
    void tearDown() {
        if (orderItem.getId() != null && orderItem.getId() > 1) {
            itemRepository.deleteById(orderItem.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        // Create the OrderItem in the database
        OrderItem createdOrderItem = orderItemService.create(orderItem);

        // Print out created order item details
        System.out.println("Created OrderItem: " + createdOrderItem);

        // Assertions
        assertNotNull(createdOrderItem);
        assertEquals(orderItem, createdOrderItem);
    }

    @Test
    @Order(2)
    void read() {
        // Retrieve the order item by ID
        OrderItem readOrderItem = orderItemService.read(orderItem.getId());

        // Print out read order item details
        System.out.println("Read OrderItem: " + readOrderItem);

        // Assertions
        assertNotNull(readOrderItem);
        assertEquals(orderItem.getId(), readOrderItem.getId());
    }

    @Test
    @Order(3)
    void update() {
        // Retrieve the order item
        OrderItem createdOrderItem = orderItemService.read(orderItem.getId());

        // Update the order item
        OrderItem updatedOrderItem = new OrderItem.Builder()
                .copy(createdOrderItem)
                .setQuantity(3)
                .build();

        // Save the updated order item
        orderItemService.update(updatedOrderItem);

        // Verify the update
        OrderItem resultOrderItem = orderItemService.read(updatedOrderItem.getId());

        // Print out updated order item details
        System.out.println("Updated OrderItem: " + resultOrderItem);

        // Assertions
        assertNotNull(resultOrderItem);
        assertEquals(3, resultOrderItem.getQuantity());
    }

    @Test
    @Order(4)
    void delete() {
        // Delete the created order item
        OrderItem orderItemD = orderItemService.create(orderItem);
        boolean deleted = orderItemService.delete(orderItemD.getId());

        // Print result of delete action
        System.out.println("OrderItem deleted: " + deleted);

        // Assertions
        assertTrue(deleted);
    }

    @Test
    @Order(5)
    void findAll() {
        // Retrieve all order items
        List<OrderItem> orderItems = orderItemService.findAll();

        // Print all order items
        System.out.println("All OrderItems: " + orderItems);

        // Assertions
        assertFalse(orderItems.isEmpty());
    }
}
