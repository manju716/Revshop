package com.model;

import java.time.LocalDateTime;

public class Review {

    private int id;
    private int productId;
    private String userEmail;
    private int rating;
    private String review;
    private LocalDateTime reviewDate;

    public Review() {}

    public Review(int productId, String userEmail, int rating, String review) {
        this.productId = productId;
        this.userEmail = userEmail;
        this.rating = rating;
        this.review = review;
        this.reviewDate = LocalDateTime.now();
    }

    public int getProductId() {
        return productId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}

