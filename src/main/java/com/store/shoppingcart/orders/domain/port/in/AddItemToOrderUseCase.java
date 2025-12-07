package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.orders.application.dto.AddItemCommand;
import com.store.shoppingcart.orders.domain.model.OrderId;

public interface AddItemToOrderUseCase {
    void execute(OrderId orderId, AddItemCommand command);
}
