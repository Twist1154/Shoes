package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.repository.OrderItemRepository;

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

    public OrderItem update(OrderItem orderItem) {
        OrderItem existingOrderItem = repository.findById(orderItem.getId()).orElse(null);
        if (existingOrderItem != null) {
            if (!existingOrderItem.getId().equals(orderItem.getId())) {
                log.error("ID mismatch while updating OrderItem. Existing ID: {}, Given ID: {}", existingOrderItem.getId(), orderItem.getId());
                return null;
            }
            OrderItem updatedOrderItem = new OrderItem.Builder()
                    .copy(existingOrderItem)
                    .setOrderDetails(orderItem.getOrderDetails())
                    .setProduct(orderItem.getProduct())
                    .setProductSku(orderItem.getProductSku())
                    .setQuantity(orderItem.getQuantity())
                    .build();
            log.info("OrderItem updated successfully. ID: {}", updatedOrderItem.getId());
            return repository.save(updatedOrderItem);
        } else {
            log.warn("Attempt to update a non-existent order item with ID: {}", orderItem.getId());
            return null;
        }
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("OrderItem deleted successfully. ID: {}", id);
            return true;
        } else {
            log.warn("Attempt to delete a non-existent OrderItem with ID: {}", id);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteOrderItemByOrderDetails_Id(Long orderId) {
        repository.deleteOrderItemByOrderDetails_Id(orderId);
    }
}