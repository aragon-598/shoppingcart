package com.store.shoppingcart.clients.application.usecase;

import com.store.shoppingcart.clients.domain.exception.ClientNotFoundException;
import com.store.shoppingcart.clients.domain.model.Address;
import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.model.PhoneNumber;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;

public class UpdateClientUseCase {
    
    private final ClientRepository clientRepository;
    
    public UpdateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public Client execute(ClientId clientId, String firstName, String lastName,
                         PhoneNumber phoneNumber, Address address) {
        
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new ClientNotFoundException(clientId));
        
        if (firstName != null && lastName != null) {
            client.updatePersonalInfo(firstName, lastName);
        }
        
        if (phoneNumber != null && address != null) {
            client.updateContactInfo(phoneNumber, address);
        }
        
        return clientRepository.save(client);
    }
}
