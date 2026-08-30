package com.sneakershop.ratings.repository;

import com.sneakershop.ratings.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Optional<Rating> findByProductIdAndUserId(
            Integer productId,
            Integer userId
    );

    List<Rating> findByProductId(Integer productId);
    List<Rating> findByProductIdOrderByCreatedAtDesc(Integer productId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.productId = ?1")
    Double getAverageRating(Integer productId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.productId = ?1")
    Long getTotalReviews(Integer productId);

}