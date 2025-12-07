package com.store.shoppingcart.clients.entrypoint.rest.dto;

import com.store.shoppingcart.clients.domain.model.DocumentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateClientRequest(
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    String firstName,
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    String lastName,
    
    @NotBlank(message = "Phone number is required")
    String phoneNumber,
    
    @Valid
    AddressRequest address
) {}
