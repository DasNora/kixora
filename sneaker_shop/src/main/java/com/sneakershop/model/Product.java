package com.sneakershop.model;

/**
 * Product is a plain Java object (POJO) that represents one row in the 'products' table.
 *
 * <p>Each product is a sneaker with an id, name, category, description, price,
 * and an image URL pointing to its Unsplash photo.</p>
 *
 * <p>Java 8 compatible — standard getter/setter pattern.</p>
 */
public class Product {

    private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String imageUrl;
    private double averageRating;

    private long totalReviews;
    
    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    // ===== Constructors =====

    public Product() { }

    public Product(String name, String category, String description,
                   double price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // ===== Getters and Setters =====

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    /**
     * Returns the price formatted with ₹ symbol for display on JSP pages.
     * Example output: "₹1,299.00" or "₹89.00"
     */
    public String getFormattedPrice() {
        return String.format("₹%,.2f", price);
    }
}