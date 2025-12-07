package com.store.shoppingcart.security.domain.exception;

import com.store.shoppingcart.security.domain.model.Email;

public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(Email email) {
        super("El usuario ya existe con el email: " + email.value());
    }
}
