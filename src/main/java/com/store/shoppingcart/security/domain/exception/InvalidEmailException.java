package com.store.shoppingcart.security.domain.exception;

public class InvalidEmailException extends RuntimeException {
    
    public InvalidEmailException(String email) {
        super("Invalid email format: " + email);
    }
}
