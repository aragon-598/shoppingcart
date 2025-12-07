package com.store.shoppingcart.security.domain.exception;

public class WeakPasswordException extends RuntimeException {
    
    public WeakPasswordException() {
        super("La contraseña debe tener al menos 8 caracteres");
    }
}
