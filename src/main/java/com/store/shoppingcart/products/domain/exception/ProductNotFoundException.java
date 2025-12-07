package com.store.shoppingcart.products.domain.exception;

import com.store.shoppingcart.products.domain.model.ProductId;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ProductId id) {
        super("Product not found: " + id.value());
    }
    
    public ProductNotFoundException(ProductId id, Throwable cause) {
        super("Product not found: " + id.value(), cause);
    }
}
