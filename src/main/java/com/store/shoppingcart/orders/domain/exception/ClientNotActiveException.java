package com.store.shoppingcart.orders.domain.exception;

import com.store.shoppingcart.clients.domain.model.ClientId;

public class ClientNotActiveException extends RuntimeException {
    
    public ClientNotActiveException(ClientId clientId) {
        super("Client is not active: " + clientId.value());
    }
}
