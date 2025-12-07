package com.store.shoppingcart.clients.infrastructure.persistence.adapter;

import com.store.shoppingcart.clients.domain.model.Client;
import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.clients.domain.model.DocumentNumber;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;
import com.store.shoppingcart.clients.infrastructure.persistence.JpaClientRepository;
import com.store.shoppingcart.clients.infrastructure.persistence.entity.ClientEntity;
import com.store.shoppingcart.clients.infrastructure.persistence.mapper.ClientMapper;
import com.store.shoppingcart.security.domain.model.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClientRepositoryAdapter implements ClientRepository {
    
    private final JpaClientRepository jpaClientRepository;
    
    public ClientRepositoryAdapter(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }
    
    @Override
    public Client save(Client client) {
        ClientEntity entity = ClientMapper.toEntity(client);
        ClientEntity saved = jpaClientRepository.save(entity);
        return ClientMapper.toDomain(saved);
    }
    
    @Override
    public Optional<Client> findById(ClientId id) {
        return jpaClientRepository.findById(id.value().toString())
            .map(ClientMapper::toDomain);
    }
    
    @Override
    public Optional<Client> findByUserId(UserId userId) {
        return jpaClientRepository.findByUserId(userId.value().toString())
            .map(ClientMapper::toDomain);
    }
    
    @Override
    public Optional<Client> findByDocumentNumber(DocumentNumber documentNumber) {
        return jpaClientRepository.findByDocumentValueAndDocumentType(
            documentNumber.value(),
            ClientMapper.toDocumentTypeEntity(documentNumber.type())
        ).map(ClientMapper::toDomain);
    }
    
    @Override
    public List<Client> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jpaClientRepository.findAllPaginated(pageable)
            .stream()
            .map(ClientMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByUserId(UserId userId) {
        return jpaClientRepository.existsByUserId(userId.value().toString());
    }
    
    @Override
    public boolean existsByDocumentNumber(DocumentNumber documentNumber) {
        return jpaClientRepository.existsByDocumentValueAndDocumentType(
            documentNumber.value(),
            ClientMapper.toDocumentTypeEntity(documentNumber.type())
        );
    }
    
    @Override
    public void deleteById(ClientId id) {
        jpaClientRepository.deleteById(id.value().toString());
    }
}
