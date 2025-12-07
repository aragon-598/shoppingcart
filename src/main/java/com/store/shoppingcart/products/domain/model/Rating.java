package com.store.shoppingcart.products.domain.model;

import com.store.shoppingcart.products.domain.exception.InvalidProductDataException;

public record Rating(double rate, int count) {
    
    private static final double MIN_RATE = 0.0;
    private static final double MAX_RATE = 5.0;
    
    public Rating {
        if (rate < MIN_RATE || rate > MAX_RATE) {
            throw new InvalidProductDataException("Rating must be between 0 and 5");
        }
        if (count < 0) {
            throw new InvalidProductDataException("Rating count cannot be negative");
        }
    }
    
    public boolean isReliable(int minCount) {
        return count >= minCount;
    }
}
