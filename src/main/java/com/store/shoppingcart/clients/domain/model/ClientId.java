package com.store.shoppingcart.clients.domain.model;

import java.util.UUID;

public record ClientId(UUID value) {
    
    public static ClientId generate() {
        return new ClientId(UUID.randomUUID());
    }
    
    public static ClientId from(String value) {
        return new ClientId(UUID.fromString(value));
    }
}
