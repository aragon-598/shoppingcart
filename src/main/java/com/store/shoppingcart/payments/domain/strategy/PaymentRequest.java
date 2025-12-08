package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.orders.domain.model.Money;
import com.store.shoppingcart.payments.domain.model.CardDetails;
import com.store.shoppingcart.payments.domain.model.PaymentMethod;

public record PaymentRequest(
    Money amount,
    PaymentMethod method,
    CardDetails cardDetails,
    String paypalEmail,
    String walletAddress,
    String cryptoCurrency
) {
}
