package com.store.shoppingcart.payments.domain.model;

import java.util.UUID;

public record PaymentId(UUID value) {
    
    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }
    
    public static PaymentId from(String value) {
        try {
            return new PaymentId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de ID de pago inválido: " + value);
        }
    }
}
