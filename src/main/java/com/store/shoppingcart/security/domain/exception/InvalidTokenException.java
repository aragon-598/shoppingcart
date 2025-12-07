package com.store.shoppingcart.security.domain.exception;

public class InvalidTokenException extends RuntimeException {
    
    public InvalidTokenException() {
        super("Invalid or expired token");
    }
}
