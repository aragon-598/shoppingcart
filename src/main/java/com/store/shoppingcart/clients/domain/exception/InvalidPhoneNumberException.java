package com.store.shoppingcart.clients.domain.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    
    public InvalidPhoneNumberException(String phoneNumber) {
        super("Invalid phone number format: " + phoneNumber);
    }
}
