package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.service.PaymentDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PaymentDetailsController.java
 *
 * This class handles HTTP requests related to payment details.
 * It provides endpoints for CRUD operations on payment details.
 *
 * Author: Rethabile Ntsekhe
 * Date: 25-Aug-24
 */
@RestController
@RequestMapping("/payment-details")
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    @Autowired
    public PaymentDetailsController(PaymentDetailsService paymentDetailsService) {
        this.paymentDetailsService = paymentDetailsService;
    }

    /**
     * Creates a new payment detail.
     *
     * @param paymentDetails the payment detail to be created
     * @return ResponseEntity containing the created PaymentDetails and HTTP status code
     */
    @PostMapping("/create")
    public ResponseEntity<PaymentDetails> createPaymentDetails(@RequestBody PaymentDetails paymentDetails) {
        if (paymentDetails == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if input is null
        }
        PaymentDetails createdPaymentDetails = paymentDetailsService.create(paymentDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentDetails);
    }

    /**
     * Retrieves a payment detail by its ID.
     *
     * @param id the ID of the payment detail to retrieve
     * @return ResponseEntity containing the PaymentDetails if found, or a 404 Not Found status if not
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<PaymentDetails> read(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if ID is null
        }
        PaymentDetails paymentDetails = paymentDetailsService.read(id);
        if (paymentDetails != null) {
            return ResponseEntity.ok(paymentDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing payment detail.
     *
     * @param id the ID of the payment detail to be updated
     * @param paymentDetails the updated payment detail details
     * @return ResponseEntity containing the updated PaymentDetails and HTTP status code, or 404 Not Found if not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentDetails> update(@PathVariable Long id, @RequestBody PaymentDetails paymentDetails) {
        if (id == null || paymentDetails == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if ID or paymentDetails are null
        }
        PaymentDetails updatedPaymentDetails = paymentDetailsService.update(paymentDetails);
        if (updatedPaymentDetails != null) {
            return ResponseEntity.ok(updatedPaymentDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a payment detail by its ID.
     *
     * @param id the ID of the payment detail to delete
     * @return ResponseEntity with HTTP status code indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if ID is null
        }
        paymentDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all payment details.
     *
     * @return ResponseEntity containing the list of all PaymentDetails and HTTP status code
     */
    @GetMapping("/all")
    public ResponseEntity<List<PaymentDetails>> getAll() {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findAll();
        if (paymentDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if the list is empty
        }
        return ResponseEntity.ok(paymentDetailsList);
    }

    /**
     * Retrieves payment details by provider.
     *
     * @param provider the provider of the payment details to retrieve
     * @return ResponseEntity containing the list of PaymentDetails with the specified provider
     */
    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<PaymentDetails>> getByProvider(@PathVariable String provider) {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findByProvider(provider);
        if (paymentDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no records found
        }
        return ResponseEntity.ok(paymentDetailsList);
    }

    /**
     * Retrieves payment details by status.
     *
     * @param status the status of the payment details to retrieve
     * @return ResponseEntity containing the list of PaymentDetails with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDetails>> getByStatus(@PathVariable String status) {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findByStatus(status);
        if (paymentDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no records found
        }
        return ResponseEntity.ok(paymentDetailsList);
    }

    /**
     * Retrieves payment details created after a specific date.
     *
     * @param createdAt the date after which payment details were created
     * @return ResponseEntity containing the list of PaymentDetails created after the specified date
     */
    @GetMapping("/created-after")
    public ResponseEntity<List<PaymentDetails>> getCreatedAfter(@RequestParam LocalDateTime createdAt) {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findByCreatedAtAfter(createdAt);
        if (paymentDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no records found
        }
        return ResponseEntity.ok(paymentDetailsList);
    }

    /**
     * Retrieves payment details created between two dates.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return ResponseEntity containing the list of PaymentDetails created between the specified dates
     */
    @GetMapping("/created-between")
    public ResponseEntity<List<PaymentDetails>> getCreatedBetween(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<PaymentDetails> paymentDetailsList = paymentDetailsService.findByCreatedAtBetween(startDate, endDate);
        if (paymentDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no records found
        }
        return ResponseEntity.ok(paymentDetailsList);
    }

    /**
     * Counts the number of payment details with a specific status.
     *
     * @param status the status of the payment details to count
     * @return ResponseEntity containing the count of PaymentDetails with the specified status
     */
    @GetMapping("/count-by-status")
    public ResponseEntity<Long> countByStatus(@RequestParam String status) {
        long count = paymentDetailsService.countByStatus(status);
        return ResponseEntity.ok(count);
    }

}
