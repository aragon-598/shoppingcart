package com.store.shoppingcart.orders.domain.model;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.domain.exception.InvalidOrderStateException;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    
    private OrderId id;
    private ClientId clientId;
    private List<OrderItem> items;
    private Money totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Order(OrderId id, ClientId clientId, List<OrderItem> items, Money totalAmount, 
                 OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.clientId = clientId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public void confirm() {
        validateCanTransitionTo(OrderStatus.CONFIRMED);
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void ship() {
        validateCanTransitionTo(OrderStatus.SHIPPED);
        this.status = OrderStatus.SHIPPED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void deliver() {
        validateCanTransitionTo(OrderStatus.DELIVERED);
        this.status = OrderStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new InvalidOrderStateException("Cannot cancel delivered order");
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public int getTotalItems() {
        return items.stream()
            .mapToInt(OrderItem::getQuantity)
            .sum();
    }
    
    public boolean isModifiable() {
        return status == OrderStatus.PENDING;
    }
    
    private void validateCanTransitionTo(OrderStatus targetStatus) {
        if (!status.canTransitionTo(targetStatus)) {
            throw new InvalidOrderStateException(
                "Cannot transition from " + status + " to " + targetStatus
            );
        }
    }
    
    public OrderId getId() {
        return id;
    }
    
    public ClientId getClientId() {
        return clientId;
    }
    
    public List<OrderItem> getItems() {
        return items;
    }
    
    public Money getTotalAmount() {
        return totalAmount;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
