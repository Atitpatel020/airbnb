package com.schedular.repository;

import com.schedular.entity.Property;
import com.schedular.entity.Review;
import com.schedular.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserRegistrationAndProperty(UserRegistration userRegistration, Property property);

    @Query("SELECT r FROM Review r WHERE r.userRegistration.id = :userId")
    List<Review> findAllReviewsByUser(@Param("userId") long userId);
}