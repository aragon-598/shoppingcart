package com.store.shoppingcart.orders.application.dto;

import com.store.shoppingcart.clients.domain.model.ClientId;

import java.util.List;

public record CreateOrderCommand(ClientId clientId, List<OrderItemCommand> items) {
}
