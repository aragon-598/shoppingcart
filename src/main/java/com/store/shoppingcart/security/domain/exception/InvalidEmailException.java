package com.store.shoppingcart.security.domain.exception;

public class InvalidEmailException extends RuntimeException {
    
    public InvalidEmailException(String email) {
        super("Formato de email inválido: " + email);
    }
}
