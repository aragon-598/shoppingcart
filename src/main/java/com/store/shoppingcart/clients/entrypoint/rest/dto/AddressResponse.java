package com.store.shoppingcart.clients.entrypoint.rest.dto;

import com.store.shoppingcart.clients.domain.model.Address;

public record AddressResponse(
    String street,
    String city,
    String state,
    String postalCode,
    String country,
    String fullAddress
) {
    public static AddressResponse from(Address address) {
        return new AddressResponse(
            address.street(),
            address.city(),
            address.state(),
            address.postalCode(),
            address.country(),
            address.fullAddress()
        );
    }
}
