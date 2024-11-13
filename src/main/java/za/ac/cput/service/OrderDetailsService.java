package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.OrderDetails;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.factory.OrderDetailsFactory;
import za.ac.cput.repository.OrderDetailsRepository;
import za.ac.cput.repository.PaymentDetailsRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderDetailsService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class OrderDetailsService implements IOrderDetails {

    private final OrderDetailsRepository repository;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public OrderDetails create(OrderDetails orderDetails) {
        return repository.save(orderDetails);

    }

    @Override
    @Transactional
    public OrderDetails read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public OrderDetails update(OrderDetails updatedOrderDetails) {
        if (repository.existsById(updatedOrderDetails.getId())) {
            OrderDetails existingOrderDetails = repository.findById(updatedOrderDetails.getId()).orElse(null);
            if (existingOrderDetails != null) {
                OrderDetails orderDetailsToUpdate = new OrderDetails.Builder()
                        .copy(existingOrderDetails)
                        .setId(existingOrderDetails.getId())
                        .setUser(updatedOrderDetails.getUser())
                        .setTotal(updatedOrderDetails.getTotal())
                        .setCreatedAt(existingOrderDetails.getCreatedAt())
                        .build();
                return repository.save(orderDetailsToUpdate);
            }
        }
        return null;
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent Order with ID: " + id);
            return false;
        }
    }

    @Override
    @Transactional
    public List<OrderDetails> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public List<OrderDetails> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    @Transactional
    public List<OrderDetails> findByTotalGreaterThanEqual(Double total) {
        return repository.findByTotalGreaterThanEqual(total);
    }

    @Override
    @Transactional
    public List<OrderDetails> findByCreatedAtAfter(LocalDateTime createdAt) {
        return repository.findByCreatedAtAfter(createdAt);
    }

    @Override
    @Transactional
    public List<OrderDetails> findByUpdatedAtBefore(LocalDateTime updatedAt) {
        return repository.findByUpdatedAtBefore(updatedAt);
    }

    @Override
    @Transactional
    public List<OrderDetails> findByPaymentId(Long paymentId) {
        return repository.findByPaymentDetails_Id(paymentId);
    }

    @Override
    @Transactional
    public List<OrderDetails> findByUserIdOrderByCreatedAtDesc(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
