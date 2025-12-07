package com.store.shoppingcart.security.domain.exception;

import com.store.shoppingcart.security.domain.model.Email;

public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(Email email) {
        super("User already exists with email: " + email.value());
    }
}
