package com.store.shoppingcart.orders.application.dto;

import com.store.shoppingcart.products.domain.model.ProductId;

public record OrderItemCommand(ProductId productId, Integer quantity) {
}
