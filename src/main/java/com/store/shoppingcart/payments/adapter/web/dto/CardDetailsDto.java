package com.store.shoppingcart.payments.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CardDetailsDto(
    @NotBlank(message = "El número de tarjeta es requerido")
    String cardNumber,
    
    @NotBlank(message = "El nombre del titular es requerido")
    String cardHolderName,
    
    @NotBlank(message = "La fecha de expiración es requerida")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$", message = "La fecha de expiración debe tener el formato MM/YY")
    String expirationDate,
    
    @NotBlank(message = "El CVV es requerido")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "El CVV debe tener 3 o 4 dígitos")
    String cvv
) {
}
