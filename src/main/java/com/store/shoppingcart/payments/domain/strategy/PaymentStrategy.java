package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.payments.domain.model.PaymentMethod;

public interface PaymentStrategy {
    
    PaymentResult process(PaymentRequest request);
    
    boolean supports(PaymentMethod method);
}
