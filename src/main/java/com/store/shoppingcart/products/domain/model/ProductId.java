package com.store.shoppingcart.products.domain.model;

import com.store.shoppingcart.products.domain.exception.InvalidProductDataException;

public record ProductId(Long value) {
    public ProductId {
        if (value == null || value <= 0) {
            throw new InvalidProductDataException("Product ID must be positive");
        }
    }
    
    public static ProductId from(String value) {
        try {
            return new ProductId(Long.parseLong(value));
        } catch (NumberFormatException e) {
            throw new InvalidProductDataException("Formato de ID de producto inválido: " + value);
        }
    }
}
