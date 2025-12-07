package com.store.shoppingcart.products.domain.exception;

import com.store.shoppingcart.products.domain.model.ProductId;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ProductId id) {
        super("Producto no encontrado: " + id.value());
    }
    
    public ProductNotFoundException(ProductId id, Throwable cause) {
        super("Producto no encontrado: " + id.value(), cause);
    }
}
