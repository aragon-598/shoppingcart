package com.store.shoppingcart.orders.domain.port.out;

import com.store.shoppingcart.clients.domain.model.ClientId;

public interface ClientValidatorPort {
    boolean exists(ClientId clientId);
    boolean isActive(ClientId clientId);
}
