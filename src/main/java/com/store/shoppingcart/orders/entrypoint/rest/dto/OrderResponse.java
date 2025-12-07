package com.store.shoppingcart.orders.entrypoint.rest.dto;

import java.util.List;

public record OrderResponse(
    String id,
    String clientId,
    List<OrderItemResponse> items,
    String totalAmount,
    String status,
    int totalItems,
    String createdAt,
    String updatedAt
) {
}
