package com.store.shoppingcart.orders.domain.model;

import java.util.UUID;

public record OrderId(UUID value) {
    
    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }
    
    public static OrderId from(String value) {
        return new OrderId(UUID.fromString(value));
    }
}
