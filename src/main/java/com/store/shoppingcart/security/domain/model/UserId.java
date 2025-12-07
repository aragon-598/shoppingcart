package com.store.shoppingcart.security.domain.model;

import java.util.UUID;

public record UserId(UUID value) {
    
    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }
    
    public static UserId from(String value) {
        return new UserId(UUID.fromString(value));
    }
}
