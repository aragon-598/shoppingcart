package com.store.shoppingcart.payments.adapter.web.dto;

import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDto(
    @NotNull(message = "El método de pago es requerido")
    PaymentMethod method,
    
    @Valid
    CardDetailsDto cardDetails,
    
    String paypalEmail,
    
    String walletAddress,
    
    String cryptoCurrency
) {
}
