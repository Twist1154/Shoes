package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.service.OrderItemService;

import java.util.List;

/**
 * OrderItemController.java
 * <p>
 * This class handles HTTP requests related to order items.
 * It provides endpoints for CRUD operations on order items.
 * <p>
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * Creates a new order item.
     *
     * @param orderItem the order item to be created
     * @return ResponseEntity containing the created OrderItem and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem createdOrderItem = orderItemService.create(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }

    /**
     * Retrieves an order item by its ID.
     *
     * @param id the ID of the order item to retrieve
     * @return ResponseEntity containing the OrderItem if found, or a 404 Not Found status if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.read(id);
        if (orderItem != null) {
            return ResponseEntity.ok(orderItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing order item.
     *
     * @param id        the ID of the order item to be updated
     * @param orderItem the updated order item details
     * @return ResponseEntity containing the updated OrderItem and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        if (!id.equals(orderItem.getId())) {
            return ResponseEntity.badRequest().build();
        }
        OrderItem updatedOrderItem = orderItemService.update(orderItem);
        if (updatedOrderItem != null) {
            return ResponseEntity.ok(updatedOrderItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes an order item by its ID.
     *
     * @param id the ID of the order item to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        boolean isDeleted = orderItemService.delete(id);
        if (isDeleted) {
            // Return success message if deleted successfully
            return ResponseEntity.status(HttpStatus.OK).body("Order item with ID " + id + " deleted successfully.");
        } else {
            // Return a not found message if the deletion failed
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order item with ID " + id + " not found.");
        }
    }

    /**
     * Retrieves all order items.
     *
     * @return ResponseEntity containing the list of all OrderItems and HTTP status code
     */
    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.findAll();
        return ResponseEntity.ok(orderItems);
    }

    @DeleteMapping("/{orderId}/items")
    public ResponseEntity<Void> deleteOrderItemsByOrderId(@PathVariable Long orderId) {
        orderItemService.deleteOrderItemByOrderDetails_Id(orderId);
        return ResponseEntity.noContent().build();
    }
}
