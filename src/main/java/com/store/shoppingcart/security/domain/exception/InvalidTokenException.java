package com.store.shoppingcart.security.domain.exception;

public class InvalidTokenException extends RuntimeException {
    
    public InvalidTokenException() {
        super("Token inválido o expirado");
    }
}
