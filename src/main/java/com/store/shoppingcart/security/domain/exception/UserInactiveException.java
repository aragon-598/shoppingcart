package com.store.shoppingcart.security.domain.exception;

public class UserInactiveException extends RuntimeException {
    
    public UserInactiveException() {
        super("La cuenta de usuario está inactiva");
    }
}
