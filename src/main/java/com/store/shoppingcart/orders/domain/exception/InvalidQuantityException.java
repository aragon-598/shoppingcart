package com.store.shoppingcart.orders.domain.exception;

public class InvalidQuantityException extends RuntimeException {
    
    public InvalidQuantityException(String message) {
        super(message);
    }
}
