package com.store.shoppingcart.payments.domain.exception;

public class PaymentNotRefundableException extends RuntimeException {
    public PaymentNotRefundableException(String message) {
        super(message);
    }
}
