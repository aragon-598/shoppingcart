package com.store.shoppingcart.payments.domain.exception;

import com.store.shoppingcart.payments.domain.model.PaymentMethod;

public class UnsupportedPaymentMethodException extends RuntimeException {
    public UnsupportedPaymentMethodException(PaymentMethod method) {
        super("Método de pago no soportado: " + method);
    }
}
