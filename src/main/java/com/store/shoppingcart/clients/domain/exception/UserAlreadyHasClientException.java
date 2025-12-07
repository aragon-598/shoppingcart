package com.store.shoppingcart.clients.domain.exception;

import com.store.shoppingcart.security.domain.model.UserId;

public class UserAlreadyHasClientException extends RuntimeException {
    
    public UserAlreadyHasClientException(UserId userId) {
        super("El usuario ya tiene un perfil de cliente: " + userId.value());
    }
}
