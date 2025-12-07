package com.store.shoppingcart.clients.application.usecase;

import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;

import java.util.List;

public class ListClientsUseCase {
    
    private final ClientRepository clientRepository;
    
    public ListClientsUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public List<Client> execute(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 20;
        }
        
        return clientRepository.findAll(page, size);
    }
}
