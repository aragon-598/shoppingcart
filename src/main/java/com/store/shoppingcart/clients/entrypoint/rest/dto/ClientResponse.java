package com.store.shoppingcart.clients.entrypoint.rest.dto;

import com.store.shoppingcart.clients.domain.model.Client;

import java.time.LocalDateTime;

public record ClientResponse(
    String id,
    String userId,
    String firstName,
    String lastName,
    String documentNumber,
    String documentType,
    String phoneNumber,
    AddressResponse address,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(
            client.getId().value().toString(),
            client.getUserId().value().toString(),
            client.getFirstName(),
            client.getLastName(),
            client.getDocumentNumber().formatted(),
            client.getDocumentNumber().type().name(),
            client.getPhoneNumber().formatted(),
            AddressResponse.from(client.getAddress()),
            client.getCreatedAt(),
            client.getUpdatedAt()
        );
    }
}
