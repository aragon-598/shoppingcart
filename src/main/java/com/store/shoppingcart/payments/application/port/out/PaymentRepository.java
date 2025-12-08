package com.store.shoppingcart.payments.application.port.out;

import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    
    Payment save(Payment payment);
    
    Optional<Payment> findById(PaymentId paymentId);
    
    List<Payment> findByOrderId(OrderId orderId);
}
