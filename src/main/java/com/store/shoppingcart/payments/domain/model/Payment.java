package com.store.shoppingcart.payments.domain.model;

import com.store.shoppingcart.orders.domain.model.Money;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.domain.exception.PaymentNotRefundableException;

import java.time.LocalDateTime;

public class Payment {
    
    private final PaymentId id;
    private final OrderId orderId;
    private final Money amount;
    private final PaymentMethod method;
    private PaymentStatus status;
    private TransactionId transactionId;
    private LocalDateTime processedAt;
    private String errorMessage;
    private final LocalDateTime createdAt;
    
    public Payment(PaymentId id, OrderId orderId, Money amount, PaymentMethod method, 
                   PaymentStatus status, TransactionId transactionId, 
                   LocalDateTime processedAt, String errorMessage) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionId = transactionId;
        this.processedAt = processedAt;
        this.errorMessage = errorMessage;
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor para crear un nuevo pago en estado PENDING
    public Payment(PaymentId id, OrderId orderId, Money amount, PaymentMethod method) {
        this(id, orderId, amount, method, PaymentStatus.PENDING, null, null, null);
    }
    
    public void markAsProcessing() {
        this.status = PaymentStatus.PROCESSING;
    }
    
    public void markAsSuccessful(TransactionId transactionId) {
        this.status = PaymentStatus.COMPLETED;
        this.transactionId = transactionId;
        this.processedAt = LocalDateTime.now();
        this.errorMessage = null;
    }
    
    public void markAsFailed(String errorMessage) {
        this.status = PaymentStatus.FAILED;
        this.errorMessage = errorMessage;
        this.processedAt = LocalDateTime.now();
    }
    
    public void refund() {
        if (status != PaymentStatus.COMPLETED) {
            throw new PaymentNotRefundableException("Solo los pagos completados pueden ser reembolsados");
        }
        this.status = PaymentStatus.REFUNDED;
    }
    
    public boolean isSuccessful() {
        return status == PaymentStatus.COMPLETED;
    }
    
    public PaymentId getId() {
        return id;
    }
    
    public OrderId getOrderId() {
        return orderId;
    }
    
    public Money getAmount() {
        return amount;
    }
    
    public PaymentMethod getMethod() {
        return method;
    }
    
    public PaymentStatus getStatus() {
        return status;
    }
    
    public TransactionId getTransactionId() {
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
}
