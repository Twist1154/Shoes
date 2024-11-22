package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.service.OrderDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderDetailsController.java
 *
 * This class handles HTTP requests related to order details.
 * It provides endpoints for CRUD operations and querying order details.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    /**
     * Creates a new order detail.
     *
     * @param orderDetails the order detail to be created
     * @return ResponseEntity containing the created OrderDetails and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails) {
        OrderDetails createdOrderDetails = orderDetailsService.create(orderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDetails);
    }

    /**
     * Retrieves an order detail by its ID.
     *
     * @param id the ID of the order detail to retrieve
     * @return ResponseEntity containing the OrderDetails if found, or a 404 Not Found status if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable Long id) {
        OrderDetails orderDetails = orderDetailsService.read(id);
        if (orderDetails != null) {
            return ResponseEntity.ok(orderDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing order detail.
     *
     * @param id the ID of the order detail to be updated
     * @param orderDetails the updated order detail details
     * @return ResponseEntity containing the updated OrderDetails and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetails(@PathVariable Long id, @RequestBody OrderDetails orderDetails) {
        OrderDetails updatedOrderDetails = orderDetailsService.update(orderDetails);
        if (updatedOrderDetails != null) {
            return ResponseEntity.ok(updatedOrderDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes an order detail by its ID.
     *
     * @param id the ID of the order detail to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        orderDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all order details.
     *
     * @return ResponseEntity containing the list of all OrderDetails and HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails() {
        List<OrderDetails> orderDetailsList = orderDetailsService.findAll();
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves order details by user ID.
     *
     * @param userId the ID of the user
     * @return ResponseEntity containing the list of OrderDetails associated with the user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByUserId(@PathVariable Long userId) {
        List<OrderDetails> orderDetailsList = orderDetailsService.findByUserId(userId);
        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves order details where total is greater than or equal to the specified amount.
     *
     * @param total the minimum total amount
     * @return ResponseEntity containing the list of OrderDetails with total greater than or equal to the specified amount
     */
    @GetMapping("/total-greater-than-equal/{total}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByTotalGreaterThanEqual(@PathVariable Double total) {
        List<OrderDetails> orderDetailsList = orderDetailsService.findByTotalGreaterThanEqual(total);
        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves order details created after the specified date.
     *
     * @param createdAt the date after which the orders were created
     * @return ResponseEntity containing the list of OrderDetails created after the specified date
     */
    @GetMapping("/created-after/{createdAt}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByCreatedAtAfter(@PathVariable String createdAt) {
        LocalDateTime createdDateTime = LocalDateTime.parse(createdAt);
        List<OrderDetails> orderDetailsList = orderDetailsService.findByCreatedAtAfter(createdDateTime);
        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves order details updated before the specified date.
     *
     * @param updatedAt the date before which the orders were updated
     * @return ResponseEntity containing the list of OrderDetails updated before the specified date
     */
    @GetMapping("/updated-before/{updatedAt}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByUpdatedAtBefore(@PathVariable String updatedAt) {
        LocalDateTime updatedDateTime = LocalDateTime.parse(updatedAt);
        List<OrderDetails> orderDetailsList = orderDetailsService.findByUpdatedAtBefore(updatedDateTime);
        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves order details by payment ID.
     *
     * @param paymentId the ID of the payment
     * @return ResponseEntity containing the list of OrderDetails associated with the payment ID
     */
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByPaymentId(@PathVariable Long paymentId) {
        List<OrderDetails> orderDetailsList = orderDetailsService.findByPaymentId(paymentId);
        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves order details by user ID, ordered by creation date in descending order.
     *
     * @param userId the ID of the user
     * @return ResponseEntity containing the list of OrderDetails associated with the user ID, ordered by creation date
     */
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByUserIdOrderByCreatedAtDesc(@PathVariable Long userId) {
        List<OrderDetails> orderDetailsList = orderDetailsService.findByUserIdOrderByCreatedAtDesc(userId);
        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderDetailsList);
    }








}
