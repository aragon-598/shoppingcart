package com.store.shoppingcart.clients.domain.model;

import com.store.shoppingcart.clients.domain.exception.InvalidPhoneNumberException;

public record PhoneNumber(String value) {
    
    private static final String PHONE_REGEX = "^\\+?[1-9]\\d{7,14}$";
    
    public PhoneNumber {
        if (value == null || !value.replaceAll("[^0-9+]", "").matches(PHONE_REGEX)) {
            throw new InvalidPhoneNumberException(value);
        }
    }
    
    public String formatted() {
        String cleaned = value.replaceAll("[^0-9]", "");
        if (cleaned.length() == 8) {
            return cleaned.substring(0, 4) + "-" + cleaned.substring(4);
        }
        return value;
    }
}
