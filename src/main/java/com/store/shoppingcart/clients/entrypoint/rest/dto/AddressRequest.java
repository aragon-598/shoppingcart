package com.store.shoppingcart.clients.entrypoint.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
    @NotBlank(message = "La calle es requerida")
    String street,
    
    @NotBlank(message = "La ciudad es requerida")
    String city,
    
    String state,
    
    String zipCode,
    
    @NotBlank(message = "El país es requerido")
    String country
) {}
