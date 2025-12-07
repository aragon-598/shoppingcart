package com.store.shoppingcart.orders.entrypoint.rest.dto;

public record OrderItemResponse(
    Long productId,
    String productName,
    String unitPrice,
    int quantity,
    String subtotal
) {
}
