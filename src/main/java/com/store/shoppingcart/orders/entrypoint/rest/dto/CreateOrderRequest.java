package com.store.shoppingcart.orders.entrypoint.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
    @NotNull(message = "El ID del cliente es requerido") 
    String clientId,
    
    @NotEmpty(message = "La orden debe contener al menos un item")
    @Valid
    List<OrderItemRequest> items
) {
}
