package com.store.shoppingcart.security.domain.model;

import com.store.shoppingcart.security.domain.exception.WeakPasswordException;
import com.store.shoppingcart.security.domain.port.out.PasswordEncoder;

public record Password(String value) {
    
    private static final int MIN_LENGTH = 8;
    
    public Password {
        if (value == null || value.length() < MIN_LENGTH) {
            throw new WeakPasswordException();
        }
    }
    
    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.value);
    }
}
