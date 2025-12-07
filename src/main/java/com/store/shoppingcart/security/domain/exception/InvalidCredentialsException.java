package com.store.shoppingcart.security.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException() {
        super("Email o contraseña inválidos");
    }
}
