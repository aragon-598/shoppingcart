package com.store.shoppingcart.clients.domain.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    
    public InvalidPhoneNumberException(String phoneNumber) {
        super("Formato de teléfono inválido: " + phoneNumber);
    }
}
