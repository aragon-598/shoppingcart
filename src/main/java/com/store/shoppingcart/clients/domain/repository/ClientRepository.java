package com.store.shoppingcart.clients.domain.repository;

import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.model.DocumentNumber;
import com.store.shoppingcart.security.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    
    Client save(Client client);
    
    Optional<Client> findById(ClientId id);
    
    Optional<Client> findByUserId(UserId userId);
    
    Optional<Client> findByDocumentNumber(DocumentNumber documentNumber);
    
    List<Client> findAll(int page, int size);
    
    boolean existsByUserId(UserId userId);
    
    boolean existsByDocumentNumber(DocumentNumber documentNumber);
    
    void deleteById(ClientId id);
}
