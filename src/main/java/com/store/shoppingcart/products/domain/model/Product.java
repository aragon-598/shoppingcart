package com.store.shoppingcart.products.domain.model;

public class Product {
    private ProductId id;
    private String title;
    private String description;
    private Price price;
    private Category category;
    private String imageUrl;
    private Rating rating;
    
    public Product(ProductId id, String title, String description, Price price, 
                   Category category, String imageUrl, Rating rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }
    
    public boolean isExpensive(Price threshold) {
        return price.isGreaterThan(threshold);
    }
    
    public boolean isInCategory(Category category) {
        return this.category == category;
    }
    
    public boolean isWellRated(double minRating) {
        return rating.rate() >= minRating;
    }
    
    public ProductId getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Price getPrice() {
        return price;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public Rating getRating() {
        return rating;
    }
}
