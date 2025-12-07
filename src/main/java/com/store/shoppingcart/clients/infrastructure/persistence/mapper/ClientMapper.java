package com.store.shoppingcart.clients.infrastructure.persistence.mapper;

import com.store.shoppingcart.clients.domain.factory.ClientFactory;
import com.store.shoppingcart.clients.domain.model.*;
import com.store.shoppingcart.clients.infrastructure.persistence.entity.ClientEntity;
import com.store.shoppingcart.clients.infrastructure.persistence.entity.DocumentTypeEntity;
import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.infrastructure.persistence.entity.UserJpaEntity;

import java.util.UUID;

public class ClientMapper {
    
    public static ClientEntity toEntity(Client client, UserJpaEntity user) {
        return new ClientEntity(
            client.getId().value().toString(),
            user,
            client.getFirstName(),
            client.getLastName(),
            client.getDocumentNumber().value(),
            toDocumentTypeEntity(client.getDocumentNumber().type()),
            client.getPhoneNumber().value(),
            client.getAddress().street(),
            client.getAddress().city(),
            client.getAddress().state(),
            client.getAddress().postalCode(),
            client.getAddress().country(),
            client.getCreatedAt(),
            client.getUpdatedAt()
        );
    }
    
    public static Client toDomain(ClientEntity entity) {
        ClientId clientId = new ClientId(UUID.fromString(entity.getId()));
        UserId userId = new UserId(UUID.fromString(entity.getUserId()));
        DocumentNumber documentNumber = new DocumentNumber(
            entity.getDocumentValue(),
            toDocumentType(entity.getDocumentType())
        );
        PhoneNumber phoneNumber = new PhoneNumber(entity.getPhoneNumber());
        Address address = new Address(
            entity.getStreet(),
            entity.getCity(),
            entity.getState(),
            entity.getPostalCode(),
            entity.getCountry()
        );
        
        return ClientFactory.reconstitute(
            clientId,
            userId,
            entity.getFirstName(),
            entity.getLastName(),
            documentNumber,
            phoneNumber,
            address,
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    public static DocumentTypeEntity toDocumentTypeEntity(DocumentType documentType) {
        return switch (documentType) {
            case DUI -> DocumentTypeEntity.DUI;
            case NIT -> DocumentTypeEntity.NIT;
            case PASSPORT -> DocumentTypeEntity.PASSPORT;
        };
    }
    
    public static DocumentType toDocumentType(DocumentTypeEntity documentTypeEntity) {
        return switch (documentTypeEntity) {
            case DUI -> DocumentType.DUI;
            case NIT -> DocumentType.NIT;
            case PASSPORT -> DocumentType.PASSPORT;
        };
    }
}
