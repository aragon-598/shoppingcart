package com.store.shoppingcart.clients.entrypoint.rest.controller;

import com.store.shoppingcart.clients.application.usecase.*;
import com.store.shoppingcart.clients.domain.model.*;
import com.store.shoppingcart.clients.entrypoint.rest.dto.ClientResponse;
import com.store.shoppingcart.clients.entrypoint.rest.dto.CreateClientRequest;
import com.store.shoppingcart.clients.entrypoint.rest.dto.UpdateClientRequest;
import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.security.domain.model.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Clients", description = "Client management endpoints")
public class ClientController {
    
    private final CreateClientUseCase createClientUseCase;
    private final FindClientUseCase findClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    
    public ClientController(CreateClientUseCase createClientUseCase,
                           FindClientUseCase findClientUseCase,
                           UpdateClientUseCase updateClientUseCase,
                           ListClientsUseCase listClientsUseCase,
                           DeleteClientUseCase deleteClientUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.findClientUseCase = findClientUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.listClientsUseCase = listClientsUseCase;
        this.deleteClientUseCase = deleteClientUseCase;
    }
    
    @PostMapping
    @Operation(summary = "Create a new client")
    public ResponseEntity<ApiResponse<ClientResponse>> createClient(@Valid @RequestBody CreateClientRequest request) {
        UserId userId = UserId.from(request.userId());
        DocumentNumber documentNumber = new DocumentNumber(request.documentNumber(), request.documentType());
        PhoneNumber phoneNumber = new PhoneNumber(request.phoneNumber());
        Address address = new Address(
            request.address().street(),
            request.address().city(),
            request.address().state(),
            request.address().zipCode(),
            request.address().country()
        );
        
        Client client = createClientUseCase.execute(
            userId,
            request.firstName(),
            request.lastName(),
            documentNumber,
            phoneNumber,
            address
        );
        
        ClientResponse response = ClientResponse.from(client);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED.value(), response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID")
    public ResponseEntity<ApiResponse<ClientResponse>> getClientById(@PathVariable String id) {
        ClientId clientId = ClientId.from(id);
        Client client = findClientUseCase.execute(clientId);
        ClientResponse response = ClientResponse.from(client);
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), response));
    }
    
    @GetMapping
    @Operation(summary = "List all clients with pagination")
    public ResponseEntity<ApiResponse<List<ClientResponse>>> listClients(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        List<Client> clients = listClientsUseCase.execute(page, size);
        List<ClientResponse> responses = clients.stream()
            .map(ClientResponse::from)
            .toList();
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), responses));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update client information")
    public ResponseEntity<ApiResponse<ClientResponse>> updateClient(
        @PathVariable String id,
        @Valid @RequestBody UpdateClientRequest request
    ) {
        ClientId clientId = ClientId.from(id);
        PhoneNumber phoneNumber = new PhoneNumber(request.phoneNumber());
        Address address = new Address(
            request.address().street(),
            request.address().city(),
            request.address().state(),
            request.address().zipCode(),
            request.address().country()
        );
        
        Client client = updateClientUseCase.execute(
            clientId,
            request.firstName(),
            request.lastName(),
            phoneNumber,
            address
        );
        
        ClientResponse response = ClientResponse.from(client);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), response));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable String id) {
        ClientId clientId = ClientId.from(id);
        deleteClientUseCase.execute(clientId);
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), null));
    }
}
