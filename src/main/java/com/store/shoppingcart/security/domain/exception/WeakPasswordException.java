package com.store.shoppingcart.security.domain.exception;

public class WeakPasswordException extends RuntimeException {
    
    public WeakPasswordException() {
        super("Password must be at least 8 characters long");
    }
}
