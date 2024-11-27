package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Review;
import za.ac.cput.service.ReviewService;

import java.util.List;

/**
 * ReviewController.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 24-Sep-24
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<Review> create(@RequestBody Review review) {
        Review newReview = reviewService.create(review);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Review> read(@PathVariable Long id) {
        Review review = reviewService.read(id);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Review> update(@RequestBody Review review) {
        Review updatedReview = reviewService.update(review);
        if (updatedReview != null) {
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> findAll() {
        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = reviewService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> findByProductId(@PathVariable Long productId) {
        List<Review> reviews = reviewService.findByProduct_Id(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> findByRating(@PathVariable int rating) {
        List<Review> reviews = reviewService.findByRating(rating);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/rating/greater-than/{rating}")
    public ResponseEntity<List<Review>> findByRatingGreaterThan(@PathVariable int rating) {
        List<Review> reviews = reviewService.findByRatingGreaterThan(rating);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
