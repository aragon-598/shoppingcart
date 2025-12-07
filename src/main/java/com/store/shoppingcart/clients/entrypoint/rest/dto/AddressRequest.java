package com.store.shoppingcart.clients.entrypoint.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
    @NotBlank(message = "Street is required")
    String street,
    
    @NotBlank(message = "City is required")
    String city,
    
    String state,
    
    String postalCode,
    
    @NotBlank(message = "Country is required")
    String country
) {}
