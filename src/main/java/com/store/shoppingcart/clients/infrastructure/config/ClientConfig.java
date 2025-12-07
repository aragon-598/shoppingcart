package com.store.shoppingcart.clients.infrastructure.config;

import com.store.shoppingcart.clients.application.usecase.*;
import com.store.shoppingcart.clients.domain.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    
    @Bean
    public CreateClientUseCase createClientUseCase(ClientRepository clientRepository) {
        return new CreateClientUseCase(clientRepository);
    }
    
    @Bean
    public FindClientUseCase findClientUseCase(ClientRepository clientRepository) {
        return new FindClientUseCase(clientRepository);
    }
    
    @Bean
    public UpdateClientUseCase updateClientUseCase(ClientRepository clientRepository) {
        return new UpdateClientUseCase(clientRepository);
    }
    
    @Bean
    public ListClientsUseCase listClientsUseCase(ClientRepository clientRepository) {
        return new ListClientsUseCase(clientRepository);
    }
    
    @Bean
    public DeleteClientUseCase deleteClientUseCase(ClientRepository clientRepository) {
        return new DeleteClientUseCase(clientRepository);
    }
}
