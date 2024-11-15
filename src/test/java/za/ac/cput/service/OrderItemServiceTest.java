package za.ac.cput.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import za.ac.cput.domain.*;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.factory.OrderItemFactory;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.repository.OrderItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest
@ActiveProfiles("test") // Use a specific profile for testing if needed
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_CLASS)
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
        Product product = productService.read(1L);
        productSku = productSkuService.read(1L);
        user = userService.read(2L);


        if (paymentDetails == null) {
            // Create and save new payment details if it doesn't exist
            paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                    null, // ID will be generated upon save
                    120.0,
                    "CreditCard",
                    "Completed"
            );
            paymentDetails = paymentDetailsService.create(paymentDetails); // Persist payment details
            System.out.println("Payment Details created with ID: " + paymentDetails.getId());
        }

        // Create order details using the newly created or retrieved paymentDetails
        orderDetails = OrderDetailsFactory.createOrderDetails(
                null, // ID will be generated upon save
                user,
                paymentDetails,
                1500.0
        );

        // Set up OrderItem
        OrderItem orderItem1 = OrderItemFactory.createOrderItem(
                1L,
                orderDetails,
                product,
                productSku,
                2
        );

        orderItem = orderItemService.create(orderItem1);
    }

    @AfterEach
    void tearDown() {
        if (orderItem.getId() != null && orderItem.getId() > 3) {
            itemRepository.deleteById(orderItem.getId());
        }
    }

    @Test
    @Order(1)
    void create() {
        OrderItem createdOrderItem = orderItemService.create(orderItem);

        // Print out created order item details
        System.out.println("Created OrderItem: " + createdOrderItem);

        assertNotNull(createdOrderItem);
        assertEquals(orderItem.getId(), createdOrderItem.getId());
    }

    @Test
    @Order(2)
    void read() {
        OrderItem readOrderItem = orderItemService.read(orderItem.getId());

        // Print out read order item details
        System.out.println("Read OrderItem: " + readOrderItem);

        assertNotNull(readOrderItem);
        assertEquals(orderItem.getId(), readOrderItem.getId());
    }

    @Test
    @Order(3)
    void update() {
        OrderItem createdOrderItem = orderItemService.read(orderItem.getId());

        OrderItem updatedOrderItem = new OrderItem.Builder()
                .copy(createdOrderItem)
                .setQuantity(3)
                .build();

        orderItemService.update(updatedOrderItem);

        OrderItem resultOrderItem = orderItemService.read(updatedOrderItem.getId());

        // Print out updated order item details
        System.out.println("Updated OrderItem: " + resultOrderItem);

        assertNotNull(resultOrderItem);
        assertEquals(3, resultOrderItem.getQuantity());
    }

    @Test
    @Order(4)
    void delete() {
        OrderItem orderItemD = orderItemService.create(orderItem);
        boolean deleted = orderItemService.delete(orderItemD.getId());

        // Print result of delete action
        System.out.println("OrderItem deleted: " + deleted);

        assertTrue(deleted);
    }

    @Test
    @Order(5)
    void findAll() {
        List<OrderItem> orderItems = orderItemService.findAll();

        // Print all order items
        System.out.println("All OrderItems: " + orderItems);

        assertFalse(orderItems.isEmpty());
    }
}
