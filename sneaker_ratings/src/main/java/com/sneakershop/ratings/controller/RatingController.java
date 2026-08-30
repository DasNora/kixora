package com.sneakershop.ratings.controller;

import com.sneakershop.ratings.dto.RatingRequest;
import com.sneakershop.ratings.dto.RatingSummary;
import com.sneakershop.ratings.dto.ReviewResponse;
import com.sneakershop.ratings.service.RatingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * Submit or update a rating.
     */
    @PostMapping
    public String submitRating(@RequestBody RatingRequest request) {

        ratingService.submitRating(request);

        return "{\"status\":\"success\"}";
    }

    /**
     * Get rating summary for one product.
     */
    @GetMapping("/all")
    public List<RatingSummary> getAllRatings(){

        return ratingService.getAllSummaries();

    }
    @GetMapping("/{productId}")
    public RatingSummary getRating(@PathVariable Integer productId) {

        return ratingService.getSummary(productId);

    }
    @GetMapping("/{productId}/reviews")
    public List<ReviewResponse> getReviews(
            @PathVariable Integer productId){

        return ratingService.getReviews(productId);

    }

}






/**package com.sneakershop.ratings.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RatingController exposes REST endpoints for the Sneaker Ratings Microservice.
 *
 * <p>GET  /api/v1/ratings/{productId} → returns average rating + total reviews
 * POST /api/v1/ratings               → accepts a new rating submission</p>
 *
 * <p>Java 8 compatible — uses HashMap and explicit type parameters.
 * Based on ReadMeAI blueprint: Section 2, Feature A + B</p>
 
@RestController
@RequestMapping("/api/v1")
public class RatingController {

    /**
     * In-memory store simulating a ratings database.
     * Key: productId (String), Value: Map with "totalScore" (sum of all ratings)
     * and "count" (number of reviews).
     *
     * In production, this would be replaced with MySQL/JPA.
     
    private static final ConcurrentHashMap<String, Map<String, Object>> ratingStore
            = new ConcurrentHashMap<String, Map<String, Object>>();

    // Seed some demo data so the first page load looks good
    static {
        Map<String, Object> demo1 = new HashMap<String, Object>();
        demo1.put("totalScore", 235.0);
        demo1.put("count", 50);
        ratingStore.put("1", demo1);

        Map<String, Object> demo2 = new HashMap<String, Object>();
        demo2.put("totalScore", 410.0);
        demo2.put("count", 95);
        ratingStore.put("2", demo2);

        Map<String, Object> demo3 = new HashMap<String, Object>();
        demo3.put("totalScore", 156.0);
        demo3.put("count", 40);
        ratingStore.put("3", demo3);
    }

    /**
     * Fetches rating data for a specific product.
     *
     * <p>Called by: ProductDetailServlet → GET http://localhost:8081/api/v1/ratings/{id}</p>
     *
     * @param productId the product ID from the URL path
     * @return JSON: {"productId":"1","averageRating":4.7,"totalReviews":50}
     
    @GetMapping("/ratings/{productId}")
    public ResponseEntity<Map<String, Object>> getProductRating(@PathVariable String productId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("productId", productId);

        Map<String, Object> stored = ratingStore.get(productId);

        if (stored != null && (Integer) stored.get("count") > 0) {
            double totalScore = (Double) stored.get("totalScore");
            int count = (Integer) stored.get("count");
            double average = Math.round((totalScore / count) * 10.0) / 10.0;

            response.put("averageRating", average);
            response.put("totalReviews", count);
        } else {
            // No ratings yet for this product
            response.put("averageRating", 0.0);
            response.put("totalReviews", 0);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Accepts a new rating submission from a user.
     *
     * <p>Called by: SubmitRatingServlet → POST http://localhost:8081/api/v1/ratings
     * with body: {"productId":"5","rating":4}</p>
     *
     * @param payload a map containing "productId" (String) and "rating" (Integer)
     * @return JSON: {"message":"Rating saved successfully"}
     
    @PostMapping("/ratings")
    public ResponseEntity<Map<String, String>> saveRating(@RequestBody Map<String, Object> payload) {
        String productId = (String) payload.get("productId");
        Integer rating = (Integer) payload.get("rating");

        if (productId == null || rating == null) {
            Map<String, String> errorResponse = new HashMap<String, String>();
            errorResponse.put("message", "Missing required fields: productId and rating");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Update the in-memory store (thread-safe via put + compute pattern)
        Map<String, Object> entry = ratingStore.get(productId);
        if (entry == null) {
            entry = new HashMap<String, Object>();
            entry.put("totalScore", 0.0);
            entry.put("count", 0);
        }

        double currentTotal = (Double) entry.get("totalScore");
        int currentCount = (Integer) entry.get("count");

        entry.put("totalScore", currentTotal + rating);
        entry.put("count", currentCount + 1);

        ratingStore.put(productId, entry);

        System.out.println("[RatingService] Saved rating " + rating + " for product " + productId);

        Map<String, String> status = new HashMap<String, String>();
        status.put("message", "Rating saved successfully");
        return ResponseEntity.ok(status);
    }
}**/