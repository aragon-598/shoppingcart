package com.store.shoppingcart.clients.domain.model;

public record Address(
    String street,
    String city,
    String state,
    String postalCode,
    String country
) {
    public Address {
        validateNotBlank(street, "Street");
        validateNotBlank(city, "City");
        validateNotBlank(country, "Country");
    }
    
    private void validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " no puede estar vacío");
        }
    }
    
    public String fullAddress() {
        StringBuilder sb = new StringBuilder(street);
        sb.append(", ").append(city);
        if (state != null && !state.isBlank()) {
            sb.append(", ").append(state);
        }
        if (postalCode != null && !postalCode.isBlank()) {
            sb.append(" ").append(postalCode);
        }
        sb.append(", ").append(country);
        return sb.toString();
    }
}
