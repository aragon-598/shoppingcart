package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.payments.domain.model.TransactionId;

public record PaymentResult(
    boolean success,
    TransactionId transactionId,
    String message
) {
    public static PaymentResult success(TransactionId transactionId) {
        return new PaymentResult(true, transactionId, "Pago procesado exitosamente");
    }
    
    public static PaymentResult failure(String message) {
        return new PaymentResult(false, null, message);
    }
}
