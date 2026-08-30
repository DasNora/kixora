package com.sneakershop.ratings.service;

import com.sneakershop.ratings.dto.RatingRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.sneakershop.ratings.dto.RatingSummary;
import com.sneakershop.ratings.dto.ReviewResponse;
import com.sneakershop.ratings.entity.Rating;
import com.sneakershop.ratings.repository.RatingRepository;
import com.sneakershop.ratings.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Save a new rating or update an existing one.
     */
    public void submitRating(RatingRequest request) {

        Optional<Rating> existing =
                ratingRepository.findByProductIdAndUserId(
                        request.getProductId(),
                        request.getUserId());

        Rating rating;

        if (existing.isPresent()) {

            // User has already rated this product
            rating = existing.get();

        } else {

            // New rating
            rating = new Rating();

            rating.setProductId(request.getProductId());
            rating.setUserId(request.getUserId());

        }

        rating.setRating(request.getRating());
        rating.setReview(request.getReview());

        ratingRepository.save(rating);
    }

    /**
     * Get rating summary for one product.
     */
    public RatingSummary getSummary(Integer productId) {

        Double average =
                ratingRepository.getAverageRating(productId);

        Long reviews =
                ratingRepository.getTotalReviews(productId);

        if (average == null) {
            average = 0.0;
        }

        if (reviews == null) {
            reviews = 0L;
        }

        return new RatingSummary(productId,average,reviews);
    }
    public List<RatingSummary> getAllSummaries() {

        List<Rating> ratings =
                ratingRepository.findAll();

        Map<Integer, List<Rating>> grouped =
                ratings.stream()
                       .collect(Collectors.groupingBy(
                               Rating::getProductId));

        List<RatingSummary> summaries =
                new ArrayList<>();

        for(Integer productId : grouped.keySet()){

            List<Rating> list =
                    grouped.get(productId);

            double average =
                    list.stream()
                        .mapToInt(Rating::getRating)
                        .average()
                        .orElse(0);

            summaries.add(

                new RatingSummary(

                        productId,

                        average,

                        (long) list.size()

                )

            );

        }

        return summaries;

    }
    public List<ReviewResponse> getReviews(Integer productId){

        List<Rating> ratings =
                ratingRepository.findByProductIdOrderByCreatedAtDesc(productId);

        List<ReviewResponse> reviews =
                new ArrayList<>();

        for(Rating r : ratings){

            ReviewResponse dto =
                    new ReviewResponse();

            dto.setRating(r.getRating());

            dto.setReview(r.getReview());

            dto.setCreatedAt(r.getCreatedAt().toString());

            com.sneakershop.ratings.entity.User user =
                    userRepository.findById(r.getUserId()).orElse(null);

            if(user != null){

                dto.setUserName(user.getFirstName());

            }else{

                dto.setUserName("Anonymous");

            }

            reviews.add(dto);

        }

        return reviews;
    }
}