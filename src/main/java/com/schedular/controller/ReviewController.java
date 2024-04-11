package com.schedular.controller;

import com.schedular.entity.Property;
import com.schedular.entity.Review;
import com.schedular.entity.UserRegistration;
import com.schedular.repository.PropertyRepository;
import com.schedular.repository.ReviewRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private PropertyRepository propertyRepository;
    private ReviewRepository reviewRepository;

    public ReviewController(PropertyRepository propertyRepository, ReviewRepository reviewRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReviews(
            @AuthenticationPrincipal UserRegistration userRegistration,
            @PathVariable long propertyId,
            @RequestBody Review review
    ) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        Property property = propertyOptional.orElseThrow(() -> new IllegalArgumentException("Property not found with id: " + propertyId));

        // Check if the user has already reviewed the property
        boolean alreadyReviewed = reviewRepository.existsByUserRegistrationAndProperty(userRegistration, property);
        if (alreadyReviewed) {
            throw new IllegalStateException("User has already reviewed this property.");
        }

        review.setProperty(property);
        review.setUserRegistration(userRegistration);
        try {
            reviewRepository.save(review);
            return ResponseEntity.ok("Review added successfully");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Failed to add review. Please try again later.");
        }
    }

    // http://localhost:8080/api/v1/reviews/editReview/1

    @PutMapping("/editReview/{reviewId}")
    public ResponseEntity<String> editReview(
            @AuthenticationPrincipal UserRegistration userRegistration,
            @PathVariable long reviewId,
            @RequestBody Review updatedReview
    ) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            // Check if the user owns the review by comparing user IDs
            if (!review.getUserRegistration().getId().equals(userRegistration.getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to edit this review.");
            }
            review.setRating(updatedReview.getRating());
            review.setContent(updatedReview.getContent());
            reviewRepository.save(review);
            return ResponseEntity.ok("Review updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // http://localhost:8080/api/v1/reviews/deleteReview/1

    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @AuthenticationPrincipal UserRegistration userRegistration,
            @PathVariable long reviewId
    ) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();

            // Check if the user is the same user who created the review
            if (userRegistration.getId().equals(review.getUserRegistration().getId())) {
                reviewRepository.deleteById(reviewId);
                return ResponseEntity.ok("Review deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this review.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    http://localhost:8080/api/v1/reviews/userReviews/2
    @GetMapping("/userReviews/{userId}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable long userId) {
        List<Review> userReviews = reviewRepository.findAllReviewsByUser(userId);
        return ResponseEntity.ok(userReviews);
    }
}



