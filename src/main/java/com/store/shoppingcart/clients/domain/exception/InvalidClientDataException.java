package com.store.shoppingcart.clients.domain.exception;

public class InvalidClientDataException extends RuntimeException {
    
    public InvalidClientDataException(String message) {
        super(message);
    }
}
