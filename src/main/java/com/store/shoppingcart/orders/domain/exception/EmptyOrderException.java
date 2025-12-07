package com.store.shoppingcart.orders.domain.exception;

public class EmptyOrderException extends RuntimeException {
    
    public EmptyOrderException() {
        super("La orden debe contener al menos un item");
    }
}
