package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.repository.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderItemService.java
 *
 * Author: Rethabile Ntsekhe
 * Student Num: 220455430
 * Date: 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class OrderItemService implements IOrderItem {

    private final OrderItemRepository repository;

    @Autowired
    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public OrderItem read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
     OrderItem   existingOrderItem = repository.findById(orderItem.getId()).orElse(null);
        if(existingOrderItem != null) {
            OrderItem updatedOrderItem = new OrderItem.Builder()
                    .copy(existingOrderItem) // Copy existing fields
                    .setOrderDetails(orderItem.getOrderDetails()) // Update new order details
                    .setProduct(orderItem.getProduct()) // Update new product
                    .setProductSku(orderItem.getProductSku()) // Update new product SKU
                    .setQuantity(orderItem.getQuantity()) // Update new quantity
                    .setCreatedAt(existingOrderItem.getCreatedAt()) // Keep original creation date
                    .setUpdatedAt(LocalDateTime.now()) // Update to current time
                    .build();
            return repository.save(updatedOrderItem);
        } else {
            log.warn("Attempt to update a non-existent order item with ID: {}", orderItem.getId());
            return null;
        }
    }

    public boolean delete(Long id) {
        repository.deleteById(id);

        // Check if the entity still exists after deletion
        boolean exists = repository.existsById(id);

        // Return false if entity was deleted successfully, otherwise return true
        return !exists;
    }

    @Override
    public List<OrderItem> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteOrderItemByOrderDetails_Id(Long orderId) {
        repository.deleteOrderItemByOrderDetails_Id(orderId);
    }
}