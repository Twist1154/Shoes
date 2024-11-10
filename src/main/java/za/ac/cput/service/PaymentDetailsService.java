package za.ac.cput.service;

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
@Service
@Transactional
public class PaymentDetailsService implements IPaymentDetails {

    private final PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    public PaymentDetailsService(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Override
    public PaymentDetails create(PaymentDetails paymentDetails) {
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails read(Long id) {
        return paymentDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentDetails update(PaymentDetails paymentDetails) {
        if (paymentDetails == null || !paymentDetailsRepository.existsById(paymentDetails.getId())) {
            throw new IllegalArgumentException("PaymentDetails with the given ID does not exist.");
        }

        PaymentDetails existingPayment = paymentDetailsRepository.findById(paymentDetails.getId()).orElseThrow();
        if (existingPayment != null) {
            PaymentDetails updatedPaymentDetails = new PaymentDetails.Builder()
                    .copy(existingPayment)
                    .setProvider(paymentDetails.getProvider())
                    .setAmount(paymentDetails.getAmount())
                    .setStatus(paymentDetails.getStatus())
                    .setCreatedAt(paymentDetails.getCreatedAt())
                    .setOrderDetails(paymentDetails.getOrderDetails())
                    .build();

            return paymentDetailsRepository.save(updatedPaymentDetails);
        }
        return null;
    }

    public boolean delete(Long id) {
        paymentDetailsRepository.deleteById(id);

        // Check if the entity still exists after deletion
        boolean exists = paymentDetailsRepository.existsById(id);

        // Return false if entity was deleted successfully, otherwise return true
        return !exists;
    }

    @Override
    public List<PaymentDetails> findAll() {
        return paymentDetailsRepository.findAll();
    }

    @Override
    public List<PaymentDetails> findByProvider(String provider) {
        return paymentDetailsRepository.findByProvider(provider);
    }

    @Override
    public List<PaymentDetails> findByStatus(String status) {
        return paymentDetailsRepository.findByStatus(status);
    }

    @Override
    public List<PaymentDetails> findByCreatedAtAfter(LocalDateTime createdAt) {
        return paymentDetailsRepository.findByCreatedAtAfter(createdAt);
    }

    @Override
    public List<PaymentDetails> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentDetailsRepository.findByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public long countByStatus(String status) {
        return paymentDetailsRepository.countByStatus(status);
    }

    @Override
    public int deleteByOrderDetailsId(Long orderDetailsId) {
        int deletedCount = paymentDetailsRepository.deleteByOrderDetailsId(orderDetailsId);
        if (deletedCount <= 0) {
            throw new IllegalArgumentException("No payment details found for the given order details ID");
        }
        return deletedCount;
    }
}
