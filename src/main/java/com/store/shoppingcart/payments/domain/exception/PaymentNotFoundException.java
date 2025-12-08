package com.store.shoppingcart.payments.domain.exception;

import com.store.shoppingcart.payments.domain.model.PaymentId;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(PaymentId paymentId) {
        super("Pago no encontrado con ID: " + paymentId.value());
    }
}
