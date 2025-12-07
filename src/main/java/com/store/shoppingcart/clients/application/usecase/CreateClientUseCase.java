package com.store.shoppingcart.clients.application.usecase;

import com.store.shoppingcart.clients.domain.exception.DuplicateDocumentException;
import com.store.shoppingcart.clients.domain.exception.UserAlreadyHasClientException;
import com.store.shoppingcart.clients.domain.factory.ClientFactory;
import com.store.shoppingcart.clients.domain.model.Address;
import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.model.DocumentNumber;
import com.store.shoppingcart.clients.domain.model.PhoneNumber;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;
import com.store.shoppingcart.security.domain.model.UserId;

public class CreateClientUseCase {
    
    private final ClientRepository clientRepository;
    
    public CreateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public Client execute(UserId userId, String firstName, String lastName, DocumentNumber documentNumber,
                         PhoneNumber phoneNumber, Address address) {
        
        if (clientRepository.existsByUserId(userId)) {
            throw new UserAlreadyHasClientException(userId);
        }
        
        if (clientRepository.existsByDocumentNumber(documentNumber)) {
            throw new DuplicateDocumentException(documentNumber);
        }
        
        Client client = ClientFactory.create(userId, firstName, lastName, documentNumber, phoneNumber, address);
        return clientRepository.save(client);
    }
}
