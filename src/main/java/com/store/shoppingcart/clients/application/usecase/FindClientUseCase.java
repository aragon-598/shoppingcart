package com.store.shoppingcart.clients.application.usecase;

import com.store.shoppingcart.clients.domain.exception.ClientNotFoundException;
import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;

public class FindClientUseCase {
    
    private final ClientRepository clientRepository;
    
    public FindClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public Client execute(ClientId clientId) {
        return clientRepository.findById(clientId)
            .orElseThrow(() -> new ClientNotFoundException(clientId));
    }
}
