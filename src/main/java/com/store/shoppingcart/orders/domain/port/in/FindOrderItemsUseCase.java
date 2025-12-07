package com.store.shoppingcart.orders.domain.port.in;

import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderItem;

import java.util.List;

public interface FindOrderItemsUseCase {
    List<OrderItem> execute(OrderId orderId);
    OrderItem findById(OrderId orderId, Long itemId);
}
