package com.sneakershop.ratings.dto;

public class RatingSummary {

    private Integer productId;
    private Double averageRating;
    private Long totalReviews;


    public RatingSummary(Integer productId,
                         Double averageRating,
                         Long totalReviews) {

        this.productId = productId;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }
}