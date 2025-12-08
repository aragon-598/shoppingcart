package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.payments.domain.exception.UnsupportedPaymentMethodException;
import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentStrategyFactory {
    
    private final List<PaymentStrategy> strategies;
    
    public PaymentStrategyFactory(List<PaymentStrategy> strategies) {
        this.strategies = strategies;
    }
    
    public PaymentStrategy getStrategy(PaymentMethod method) {
        return strategies.stream()
            .filter(strategy -> strategy.supports(method))
            .findFirst()
            .orElseThrow(() -> new UnsupportedPaymentMethodException(method));
    }
}
