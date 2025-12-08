package com.store.shoppingcart.payments.domain.exception;

import com.store.shoppingcart.orders.domain.model.OrderId;

public class OrderNotPayableException extends RuntimeException {
    public OrderNotPayableException(OrderId orderId, String reason) {
        super("La orden " + orderId.value() + " no puede ser pagada: " + reason);
    }
}
