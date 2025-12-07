package com.store.shoppingcart.clients.entrypoint.rest.dto;

import com.store.shoppingcart.clients.domain.model.DocumentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
    @NotBlank(message = "User ID is required")
    String userId,
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    String firstName,
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    String lastName,
    
    @NotBlank(message = "Document number is required")
    String documentNumber,
    
    @NotNull(message = "Document type is required")
    DocumentType documentType,
    
    @NotBlank(message = "Phone number is required")
    String phoneNumber,
    
    @NotNull(message = "Address is required")
    @Valid
    AddressRequest address
) {}
