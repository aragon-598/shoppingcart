package com.store.shoppingcart.clients.domain.factory;

import com.store.shoppingcart.clients.domain.exception.InvalidClientDataException;
import com.store.shoppingcart.clients.domain.model.Address;
import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.model.DocumentNumber;
import com.store.shoppingcart.clients.domain.model.PhoneNumber;
import com.store.shoppingcart.security.domain.model.UserId;

import java.time.LocalDateTime;

public class ClientFactory {
    
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;
    private static final String NAME_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    
    public static Client create(UserId userId, String firstName, String lastName, DocumentNumber documentNumber,
                                PhoneNumber phoneNumber, Address address) {
        validateName(firstName, "First name");
        validateName(lastName, "Last name");
        
        LocalDateTime now = LocalDateTime.now();
        return new Client(
            ClientId.generate(),
            userId,
            firstName.trim(),
            lastName.trim(),
            documentNumber,
            phoneNumber,
            address,
            now,
            now
        );
    }
    
    public static Client reconstitute(ClientId id, UserId userId, String firstName, String lastName,
                                     DocumentNumber documentNumber, PhoneNumber phoneNumber,
                                     Address address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Client(id, userId, firstName, lastName, documentNumber, phoneNumber, address, createdAt, updatedAt);
    }
    
    private static void validateName(String name, String fieldName) {
        if (name == null || name.isBlank()) {
            throw new InvalidClientDataException(fieldName + " cannot be blank");
        }
        
        String trimmed = name.trim();
        
        if (trimmed.length() < MIN_NAME_LENGTH || trimmed.length() > MAX_NAME_LENGTH) {
            throw new InvalidClientDataException(
                fieldName + " must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters"
            );
        }
        
        if (!trimmed.matches(NAME_REGEX)) {
            throw new InvalidClientDataException(fieldName + " can only contain letters and spaces");
        }
    }
}
