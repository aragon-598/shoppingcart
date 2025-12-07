package com.store.shoppingcart.orders.entrypoint.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddItemRequest(
    @NotNull(message = "El ID del producto es requerido")
    Long productId,
    
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Max(value = 999, message = "La cantidad no puede exceder 999")
    Integer quantity
) {
}
