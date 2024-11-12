package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Review;
import za.ac.cput.repository.ReviewRepository;

import java.util.List;

/**
 * ReviewService.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 24-Sep-24
 */
@Slf4j
@Transactional
@Service
public class ReviewService implements IReview {

    private final ReviewRepository repository;


    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false)
    public Review create(Review review) {
        return repository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Review read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public Review update(Review review) {
        Review existingReview = repository.findById(review.getId()).orElse(null);
        if (existingReview != null) {
            Review createdReview = new Review.Builder()
                    .copy(existingReview)
                    .setId(existingReview.getId())
                    .setReview(review.getReview())
                    .setRating(review.getRating())
                    .setProduct(review.getProduct())
                    .setUser(review.getUser())
                    .setCreatedAt(existingReview.getCreatedAt())
                    .build();
            return repository.save(createdReview);
        }

        log.warn("Attempt to update a non-existent product review with ID: {}", review.getId());
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        log.warn("Attempt to delete a non-existent review with ID: ", id);
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findByProduct_Id(Long id) {
        return repository.findByProduct_Id(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findByRating(int rating) {
        return repository.findByRating(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findByRatingGreaterThan(int rating) {
        return repository.findByRatingGreaterThan(rating);
    }
}
