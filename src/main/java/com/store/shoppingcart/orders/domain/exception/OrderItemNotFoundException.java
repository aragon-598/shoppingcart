package com.store.shoppingcart.orders.domain.exception;

public class OrderItemNotFoundException extends RuntimeException {
    
    public OrderItemNotFoundException(Long itemId) {
        super("Item de orden no encontrado con id: " + itemId);
    }
}
