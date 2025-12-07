package com.store.shoppingcart.clients.entrypoint.rest.dto;

import com.store.shoppingcart.clients.domain.model.DocumentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
    @NotBlank(message = "El ID de usuario es requerido")
    String userId,
    
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String firstName,
    
    @NotBlank(message = "El apellido es requerido")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    String lastName,
    
    @NotBlank(message = "El número de documento es requerido")
    String documentNumber,
    
    @NotNull(message = "El tipo de documento es requerido")
    DocumentType documentType,
    
    @NotBlank(message = "El número de teléfono es requerido")
    String phoneNumber,
    
    @NotNull(message = "La dirección es requerida")
    @Valid
    AddressRequest address
) {}
