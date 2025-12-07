package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.orders.domain.model.OrderId;

public interface UpdateOrderStatusUseCase {
    void confirm(OrderId orderId);
    void ship(OrderId orderId);
    void deliver(OrderId orderId);
}
