package com.store.shoppingcart.payments.application.port.in;

import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;

import java.util.List;

public interface FindPaymentUseCase {
    
    Payment findById(PaymentId paymentId);
    
    List<Payment> findByOrderId(OrderId orderId);
}
