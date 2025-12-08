package com.store.shoppingcart.payments.application.port.in;

import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;

public interface RefundPaymentUseCase {
    
    Payment refundPayment(PaymentId paymentId);
}
