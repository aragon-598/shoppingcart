package com.store.shoppingcart.orders.domain.exception;

import com.store.shoppingcart.orders.domain.model.OrderId;

public class OrderNotFoundException extends RuntimeException {
    
    public OrderNotFoundException(OrderId orderId) {
        super("Order not found with id: " + orderId.value());
    }
}
