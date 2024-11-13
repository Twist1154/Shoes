package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.repository.PaymentDetailsRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PaymentDetailsService.java
 * <p>
 * Service implementation for managing {@link PaymentDetails} entities.
 * Provides CRUD operations and additional methods for business logic.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
@Slf4j
@Service
@Transactional
public class PaymentDetailsService implements IPaymentDetails {

    private final PaymentDetailsRepository repository;

    @Autowired
    public PaymentDetailsService(PaymentDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false)
    public PaymentDetails create(PaymentDetails paymentDetails) {
        return repository.save(paymentDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDetails read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public PaymentDetails update(PaymentDetails paymentDetails) {
        if (paymentDetails == null || !repository.existsById(paymentDetails.getId())) {
            throw new IllegalArgumentException("PaymentDetails with the given ID does not exist.");
        }

        PaymentDetails existingPayment = repository.findById(paymentDetails.getId()).orElseThrow();
        if (existingPayment != null) {
            PaymentDetails updatedPaymentDetails = new PaymentDetails.Builder()
                    .copy(existingPayment)
                    .setId(existingPayment.getId())
                    .setProvider(paymentDetails.getProvider())
                    .setAmount(paymentDetails.getAmount())
                    .setStatus(paymentDetails.getStatus())
                    .setCreatedAt(existingPayment.getCreatedAt())
                    .build();

            return repository.save(updatedPaymentDetails);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id); // Return true if deleted successfully
        } else {
            log.warn("Attempt to delete a non-existent payment with ID: " + id);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByAmount(double amount) {
        return repository.findByAmount(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByAmountGreaterThan(double amount) {
        return repository.findByAmountGreaterThan(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByAmountLessThan(double amount) {
        return repository.findByAmountLessThan(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByProvider(String provider) {
        return repository.findByProvider(provider);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByCreatedAtAfter(LocalDateTime createdAt) {
        return repository.findByCreatedAtAfter(createdAt);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetails> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByCreatedAtBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        return repository.countByStatus(status);
    }

}
