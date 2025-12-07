package com.store.shoppingcart.orders.domain.exception;

public class EmptyOrderException extends RuntimeException {
    
    public EmptyOrderException() {
        super("Order must contain at least one item");
    }
}
