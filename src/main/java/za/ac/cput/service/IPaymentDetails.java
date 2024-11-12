package za.ac.cput.service;

import za.ac.cput.domain.PaymentDetails;

import java.time.LocalDateTime;
import java.util.List;

/**
 * IPaymentDetailsService.java
 *
 * Service interface for managing {@link PaymentDetails} entities.
 * Extends the generic {@link IService} interface and includes additional service methods for non-CRUD operations.
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 25-Aug-24
 */
public interface IPaymentDetails extends IService<PaymentDetails, Long> {

    /**
     * Finds all {@link PaymentDetails} by the payment provider.
     *
     * @param provider the payment provider name
     * @return a list of {@link PaymentDetails} associated with the specified provider
     */
    List<PaymentDetails> findByProvider(String provider);

    /**
     * Finds all {@link PaymentDetails} by the payment status.
     *
     * @param status the payment status
     * @return a list of {@link PaymentDetails} with the specified status
     */
    List<PaymentDetails> findByStatus(String status);

    /**
     * Finds all {@link PaymentDetails} created after the specified date.
     *
     * @param createdAt the date after which the payment details were created
     * @return a list of {@link PaymentDetails} created after the specified date
     */
    List<PaymentDetails> findByCreatedAtAfter(LocalDateTime createdAt);

    /**
     * Finds all {@link PaymentDetails} created between the specified dates.
     *
     * @param startDate the start date of the creation period
     * @param endDate   the end date of the creation period
     * @return a list of {@link PaymentDetails} created between the specified dates
     */
    List<PaymentDetails> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Counts the number of {@link PaymentDetails} with the specified status.
     *
     * @param status the payment status
     * @return the count of {@link PaymentDetails} with the specified status
     */
    long countByStatus(String status);

}
