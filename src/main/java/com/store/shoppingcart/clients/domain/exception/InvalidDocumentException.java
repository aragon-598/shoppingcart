package com.store.shoppingcart.clients.domain.exception;

public class InvalidDocumentException extends RuntimeException {
    
    public InvalidDocumentException(String message) {
        super(message);
    }
}
