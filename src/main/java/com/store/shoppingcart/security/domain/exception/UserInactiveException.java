package com.store.shoppingcart.security.domain.exception;

public class UserInactiveException extends RuntimeException {
    
    public UserInactiveException() {
        super("User account is inactive");
    }
}
