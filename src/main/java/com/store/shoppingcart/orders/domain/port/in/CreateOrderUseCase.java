package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.orders.application.dto.CreateOrderCommand;
import com.store.shoppingcart.orders.domain.model.OrderId;

public interface CreateOrderUseCase {
    OrderId execute(CreateOrderCommand command);
}
