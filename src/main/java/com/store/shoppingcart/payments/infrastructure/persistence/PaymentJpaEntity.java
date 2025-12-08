package com.store.shoppingcart.payments.infrastructure.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payments_order_id", columnList = "order_id"),
    @Index(name = "idx_payments_status", columnList = "status"),
    @Index(name = "idx_payments_transaction_id", columnList = "transaction_id")
})
public class PaymentJpaEntity {
    
    @Id
    @Column(length = 36)
    private String id;
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false, length = 3)
    private String currency;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PaymentMethodEntity method;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private PaymentStatusEntity status;
    
    @Column(name = "transaction_id", length = 50)
    private String transactionId;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Column(name = "error_message", length = 500)
    private String errorMessage;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    public PaymentJpaEntity() {
    }
    
    public PaymentJpaEntity(
            String id,
            String orderId,
            BigDecimal amount,
            String currency,
            PaymentMethodEntity method,
            PaymentStatusEntity status,
            String transactionId,
            LocalDateTime processedAt,
            String errorMessage,
            LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.method = method;
        this.status = status;
        this.transactionId = transactionId;
        this.processedAt = processedAt;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public PaymentMethodEntity getMethod() {
        return method;
    }
    
    public PaymentStatusEntity getStatus() {
        return status;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public void setMethod(PaymentMethodEntity method) {
        this.method = method;
    }
    
    public void setStatus(PaymentStatusEntity status) {
        this.status = status;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public enum PaymentMethodEntity {
        CARD, PAYPAL, CRYPTO
    }
    
    public enum PaymentStatusEntity {
        PENDING, PROCESSING, COMPLETED, FAILED, REFUNDED
    }
}
