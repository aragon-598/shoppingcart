package com.store.shoppingcart.clients.application.usecase;

import com.store.shoppingcart.clients.domain.exception.ClientNotFoundException;
import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;

public class DeleteClientUseCase {
    
    private final ClientRepository clientRepository;
    
    public DeleteClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public void execute(ClientId clientId) {
        if (!clientRepository.findById(clientId).isPresent()) {
            throw new ClientNotFoundException(clientId);
        }
        
        clientRepository.deleteById(clientId);
    }
}
