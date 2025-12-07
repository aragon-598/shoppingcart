package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.orders.domain.model.OrderId;

public interface RemoveItemFromOrderUseCase {
    void execute(OrderId orderId, Long itemId);
}
