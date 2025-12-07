package com.store.shoppingcart.clients.domain.exception;

import com.store.shoppingcart.clients.domain.model.ClientId;

public class ClientNotFoundException extends RuntimeException {
    
    public ClientNotFoundException(ClientId clientId) {
        super("Client not found with ID: " + clientId.value());
    }
}
