package com.store.shoppingcart.orders.entrypoint.rest.dto;

public record OrderItemResponse(
    Long id,
    Long productId,
    String productName,
    String unitPrice,
    Integer quantity,
    String subtotal
) {
}
