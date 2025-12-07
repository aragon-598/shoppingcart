package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;

import java.util.List;

public interface FindOrderUseCase {
    Order execute(OrderId orderId);
    List<Order> findByClient(ClientId clientId);
}
