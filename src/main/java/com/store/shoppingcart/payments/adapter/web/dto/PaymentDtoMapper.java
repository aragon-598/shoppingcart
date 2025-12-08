package com.store.shoppingcart.payments.adapter.web.dto;

import com.store.shoppingcart.orders.domain.model.Money;
import com.store.shoppingcart.payments.domain.model.CardDetails;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.strategy.PaymentRequest;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class PaymentDtoMapper {
    
    public PaymentRequest toDomainRequest(PaymentRequestDto dto, Money amount) {
        CardDetails cardDetails = null;
        if (dto.cardDetails() != null) {
            String[] parts = dto.cardDetails().expirationDate().split("/");
            String month = parts[0];
            String year = String.valueOf(2000 + Integer.parseInt(parts[1])); // Convertir YY a YYYY
            
            cardDetails = new CardDetails(
                dto.cardDetails().cardNumber(),
                dto.cardDetails().cardHolderName(),
                month,
                year,
                dto.cardDetails().cvv()
            );
        }
        
        return new PaymentRequest(
            amount,
            dto.method(),
            cardDetails,
            dto.paypalEmail(),
            dto.walletAddress(),
            dto.cryptoCurrency()
        );
    }
    
    public PaymentResponseDto toDto(Payment payment) {
        return new PaymentResponseDto(
            payment.getId().value().toString(),
            payment.getOrderId().value().toString(),
            payment.getAmount().amount(),
            payment.getAmount().currency().getCurrencyCode(),
            payment.getMethod(),
            payment.getStatus(),
            payment.getTransactionId() != null ? payment.getTransactionId().value() : null,
            payment.getProcessedAt() != null 
                ? payment.getProcessedAt().atZone(ZoneId.systemDefault()).toInstant()
                : null,
            payment.getErrorMessage(),
            payment.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}
