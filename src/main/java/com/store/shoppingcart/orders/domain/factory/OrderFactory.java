package com.store.shoppingcart.orders.domain.factory;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.application.dto.OrderItemCommand;
import com.store.shoppingcart.orders.domain.model.Order;

import java.util.List;

public interface OrderFactory {
    Order create(ClientId clientId, List<OrderItemCommand> items);
}
