package com.store.shoppingcart.payments.infrastructure.persistence;

import com.store.shoppingcart.orders.domain.model.Money;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Currency;

@Component
public class PaymentMapper {
    
    public PaymentJpaEntity toEntity(Payment payment) {
        return new PaymentJpaEntity(
            payment.getId().value().toString(),
            payment.getOrderId().value().toString(),
            payment.getAmount().amount(),
            payment.getAmount().currency().getCurrencyCode(),
            toEntityMethod(payment.getMethod()),
            toEntityStatus(payment.getStatus()),
            payment.getTransactionId() != null ? payment.getTransactionId().value() : null,
            payment.getProcessedAt(),
            payment.getErrorMessage(),
            payment.getCreatedAt()
        );
    }
    
    public Payment toDomain(PaymentJpaEntity entity) {
        Payment payment = new Payment(
            PaymentId.from(entity.getId()),
            OrderId.from(entity.getOrderId()),
            new Money(entity.getAmount(), Currency.getInstance(entity.getCurrency())),
            toDomainMethod(entity.getMethod()),
            toDomainStatus(entity.getStatus()),
            entity.getTransactionId() != null ? TransactionId.from(entity.getTransactionId()) : null,
            entity.getProcessedAt(),
            entity.getErrorMessage()
        );
        
        return payment;
    }
    
    private PaymentJpaEntity.PaymentMethodEntity toEntityMethod(PaymentMethod method) {
        return switch (method) {
            case CARD -> PaymentJpaEntity.PaymentMethodEntity.CARD;
            case PAYPAL -> PaymentJpaEntity.PaymentMethodEntity.PAYPAL;
            case CRYPTO -> PaymentJpaEntity.PaymentMethodEntity.CRYPTO;
        };
    }
    
    private PaymentMethod toDomainMethod(PaymentJpaEntity.PaymentMethodEntity method) {
        return switch (method) {
            case CARD -> PaymentMethod.CARD;
            case PAYPAL -> PaymentMethod.PAYPAL;
            case CRYPTO -> PaymentMethod.CRYPTO;
        };
    }
    
    private PaymentJpaEntity.PaymentStatusEntity toEntityStatus(PaymentStatus status) {
        return switch (status) {
            case PENDING -> PaymentJpaEntity.PaymentStatusEntity.PENDING;
            case PROCESSING -> PaymentJpaEntity.PaymentStatusEntity.PROCESSING;
            case COMPLETED -> PaymentJpaEntity.PaymentStatusEntity.COMPLETED;
            case FAILED -> PaymentJpaEntity.PaymentStatusEntity.FAILED;
            case REFUNDED -> PaymentJpaEntity.PaymentStatusEntity.REFUNDED;
        };
    }
    
    private PaymentStatus toDomainStatus(PaymentJpaEntity.PaymentStatusEntity status) {
        return switch (status) {
            case PENDING -> PaymentStatus.PENDING;
            case PROCESSING -> PaymentStatus.PROCESSING;
            case COMPLETED -> PaymentStatus.COMPLETED;
            case FAILED -> PaymentStatus.FAILED;
            case REFUNDED -> PaymentStatus.REFUNDED;
        };
    }
}
