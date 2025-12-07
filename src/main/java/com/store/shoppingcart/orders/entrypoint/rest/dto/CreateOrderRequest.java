package com.store.shoppingcart.orders.entrypoint.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
    @NotNull(message = "Client ID is required") 
    String clientId,
    
    @NotEmpty(message = "Order must contain at least one item")
    @Valid 
    List<OrderItemRequest> items
) {
}
