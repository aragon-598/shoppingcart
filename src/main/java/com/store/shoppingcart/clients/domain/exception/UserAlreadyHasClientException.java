package com.store.shoppingcart.clients.domain.exception;

import com.store.shoppingcart.security.domain.model.UserId;

public class UserAlreadyHasClientException extends RuntimeException {
    
    public UserAlreadyHasClientException(UserId userId) {
        super("User already has a client profile: " + userId.value());
    }
}
