package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.*;
import za.ac.cput.factory.OrderItemFactory;
import za.ac.cput.factory.PaymentDetailsFactory;
import za.ac.cput.service.OrderDetailsService;
import za.ac.cput.service.OrderItemService;
import za.ac.cput.service.ProductService;
import za.ac.cput.service.ProductSkuService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderDetailsService orderDetailsService;

    private final String baseUrl ="http://localhost:8080/api/order-items";

    private OrderItem orderItem;

    private ProductSku productSku;


    @BeforeEach
    void setUp() {
        Product product = productService.read(1L);
        productSku = productSkuService.read(1L);
        OrderDetails orderDetails = orderDetailsService.read(1L);

        // Set up OrderItem
         orderItem = OrderItemFactory.createOrderItem(
                1L,
                orderDetails,
                product,
                productSku,
                2
        );

        restTemplate.postForEntity(baseUrl, orderItem, OrderItem.class);

    }

    @AfterEach
    void tearDown() {
        if(orderItem != null && orderItem.getId() != null && orderItem.getId() >=3)
        orderItemService.delete(orderItem.getId());
    }

    @Test
    void createOrderItem() {
        OrderItem orderItem = OrderItemFactory.createOrderItem(
                1L,
                orderDetailsService.read(1L),
                productService.read(1L),
                productSkuService.read(1L),
                2
        );
        ResponseEntity<OrderItem> response = restTemplate.postForEntity(baseUrl, orderItem, OrderItem.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void getOrderItemById() {
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(baseUrl + "/" + orderItem.getId(), OrderItem.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
    }

    @Test
    void updateOrderItem() {
        orderItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(5)
                .build();
        restTemplate.put(baseUrl + "/" + orderItem.getId(), orderItem);
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(baseUrl + "/" + orderItem.getId(), OrderItem.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getQuantity());
    }

    @Test
    void deleteOrderItem() {
        restTemplate.delete(baseUrl + "/" + orderItem.getId());
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(baseUrl + "/" + orderItem.getId(), OrderItem.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllOrderItems() {
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(baseUrl, OrderItem[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
}