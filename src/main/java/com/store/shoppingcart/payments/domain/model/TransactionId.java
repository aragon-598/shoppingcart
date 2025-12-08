package com.store.shoppingcart.payments.domain.model;

import java.util.UUID;

public record TransactionId(String value) {
    
    public TransactionId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El ID de transacción no puede estar vacío");
        }
    }
    
    public static TransactionId from(String value) {
        return new TransactionId(value);
    }
    
    public static TransactionId generate(PaymentMethod method) {
        String prefix = switch (method) {
            case CARD -> "CARD";
            case PAYPAL -> "PP";
            case CRYPTO -> "BTC";
        };
        return new TransactionId(prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }
}
