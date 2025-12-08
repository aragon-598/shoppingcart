package com.store.shoppingcart.payments.application.port.in;

import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import com.store.shoppingcart.payments.domain.strategy.PaymentRequest;

public interface ProcessPaymentUseCase {
    
    Payment processPayment(OrderId orderId, PaymentRequest paymentRequest);
}
