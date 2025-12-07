package com.store.shoppingcart.orders.domain.model;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.domain.exception.EmptyOrderException;
import com.store.shoppingcart.orders.domain.exception.InvalidOrderStateException;
import com.store.shoppingcart.orders.domain.exception.OrderItemNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        this.items = new ArrayList<>(items);
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
            throw new InvalidOrderStateException("No se puede cancelar una orden entregada");
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
                "No se puede transicionar de " + status + " a " + targetStatus
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
    
    public void addItem(OrderItem item) {
        // Buscar si ya existe un item con el mismo producto
        Optional<OrderItem> existingItem = items.stream()
            .filter(i -> i.getProductId().equals(item.getProductId()))
            .findFirst();
        
        if (existingItem.isPresent()) {
            // Si existe, incrementar la cantidad
            existingItem.get().increaseQuantity(item.getQuantity());
        } else {
            // Si no existe, agregar el nuevo item
            this.items.add(item);
        }
        
        recalculateTotal();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void removeItem(Long itemId) {
        boolean removed = items.removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new OrderItemNotFoundException(itemId);
        }
        if (items.isEmpty()) {
            throw new EmptyOrderException();
        }
        recalculateTotal();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Optional<OrderItem> findItem(Long itemId) {
        return items.stream()
            .filter(item -> item.getId().equals(itemId))
            .findFirst();
    }
    
    public void recalculateTotal() {
        this.totalAmount = items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(Money.zero("USD"), Money::add);
    }
}
