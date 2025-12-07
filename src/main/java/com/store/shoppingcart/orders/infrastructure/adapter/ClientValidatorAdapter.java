package com.store.shoppingcart.orders.infrastructure.adapter;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;
import com.store.shoppingcart.orders.domain.port.out.ClientValidatorPort;
import org.springframework.stereotype.Component;

@Component
public class ClientValidatorAdapter implements ClientValidatorPort {
    
    private final ClientRepository clientRepository;
    
    public ClientValidatorAdapter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    @Override
    public boolean exists(ClientId clientId) {
        return clientRepository.findById(clientId).isPresent();
    }
    
    @Override
    public boolean isActive(ClientId clientId) {
        return clientRepository.findById(clientId)
            .isPresent();
    }
}
