package com.store.shoppingcart.payments.adapter.web.dto;

import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import com.store.shoppingcart.payments.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponseDto(
    String id,
    String orderId,
    BigDecimal amount,
    String currency,
    PaymentMethod method,
    PaymentStatus status,
    String transactionId,
    Instant processedAt,
    String errorMessage,
    Instant createdAt
) {
}
