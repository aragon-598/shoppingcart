package com.store.shoppingcart.orders.domain.exception;

import com.store.shoppingcart.orders.domain.model.OrderId;

public class OrderNotModifiableException extends RuntimeException {
    
    public OrderNotModifiableException(OrderId orderId) {
        super("La orden no puede ser modificada: " + orderId.value());
    }
}
