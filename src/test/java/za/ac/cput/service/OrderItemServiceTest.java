package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.domain.*;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.factory.*;
import za.ac.cput.repository.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderItemServiceTest {
    @Autowired
    private OrderItemRepository itemRepository;
    @Autowired
    private OrderItemService orderItemService;

    private OrderItem orderItem;
    private za.ac.cput.domain.ProductSku productSku;
    private User user;
    private PaymentDetails paymentDetails;

    @Autowired
    private PaymentDetailsService paymentDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductAttributeService productAttributeService;

    @BeforeEach
    void setUp() {
        // Fetch existing user and payment details
        user = userService.read(2L);
        paymentDetails = paymentDetailsService.read(35L);
        Product product = productService.read(16L);

        // Set up Product attributes and save them
        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10"
        );
        sizeAttribute = productAttributeService.create(sizeAttribute); // Ensure it's saved

        ProductAttribute colorAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.COLOR,
                "Green"
        );
        colorAttribute = productAttributeService.create(colorAttribute); // Ensure it's saved

        ProductAttribute brandAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.BRAND,
                "Nike"
        );
        brandAttribute = productAttributeService.create(brandAttribute); // Ensure it's saved

        // Generate a unique SKU for each test run
        String uniqueSku = "SKU-" + System.currentTimeMillis();

        // Create Product SKU
        productSku = ProductSkuFactory.createProductSku(
                null,
                product,
                sizeAttribute,
                colorAttribute,
                brandAttribute,
                uniqueSku,
                100.0,
                10
        );
        productSku = productSkuService.create(productSku); // Persist SKU

        // Set up OrderDetails
        OrderDetails orderDetails = OrderDetailsFactory.createOrderDetails(
                1L,
                user,
                paymentDetails,
                100.0,
                LocalDateTime.now(),
                LocalDateTime.parse("2024-06-12T00:00:00")
        );

        // Set up OrderItem
        orderItem = OrderItemFactory.createOrderItem(
                1L,
                orderDetails,
                product,
                productSku,
                2
        );

        // Persist OrderItem
        orderItem = orderItemService.create(orderItem);
    }

    @AfterEach
    void tearDown() {
        // Clear repository after each test if needed
        /*itemRepository.deleteAll();*/
    }

    @Test
    @Order(1)
    void create() {
        OrderItem createdOrderItem = orderItemService.create(orderItem);
        Assertions.assertNotNull(createdOrderItem);
        Assertions.assertEquals(orderItem.getId(), createdOrderItem.getId());
    }

    @Test
    @Order(2)
    void read() {
        OrderItem readOrderItem = orderItemService.read(orderItem.getId());
        System.out.println(readOrderItem);
        Assertions.assertNotNull(readOrderItem);
        Assertions.assertEquals(orderItem.getId(), readOrderItem.getId());
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
        Assertions.assertNotNull(resultOrderItem);
        Assertions.assertEquals(3, resultOrderItem.getQuantity()); // Assert quantity updated
    }

    @Test
    @Order(4)
    void delete() {
        orderItemService.delete(orderItem.getId());
        OrderItem deletedOrderItem = orderItemService.read(orderItem.getId());
        Assertions.assertNull(deletedOrderItem); // Check that it is deleted
    }

    @Test
    @Order(5)
    void findAll() {
        List<OrderItem> orderItems = orderItemService.findAll();
        Assertions.assertFalse(orderItems.isEmpty());
    }
}
