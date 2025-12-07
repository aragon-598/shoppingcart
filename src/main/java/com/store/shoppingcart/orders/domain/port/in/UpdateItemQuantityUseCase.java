package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.orders.application.dto.UpdateQuantityCommand;
import com.store.shoppingcart.orders.domain.model.OrderId;

public interface UpdateItemQuantityUseCase {
    void execute(OrderId orderId, Long itemId, UpdateQuantityCommand command);
}
