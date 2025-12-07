package com.store.shoppingcart.clients.domain.model;

import com.store.shoppingcart.security.domain.model.UserId;

import java.time.LocalDateTime;

public class Client {
    
    private ClientId id;
    private UserId userId;
    private String firstName;
    private String lastName;
    private DocumentNumber documentNumber;
    private PhoneNumber phoneNumber;
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Client(ClientId id, UserId userId, String firstName, String lastName, DocumentNumber documentNumber,
                  PhoneNumber phoneNumber, Address address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public void updatePersonalInfo(String firstName, String lastName) {
        this.firstName = validateNotBlank(firstName);
        this.lastName = validateNotBlank(lastName);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateContactInfo(PhoneNumber phoneNumber, Address address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    private String validateNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El valor no puede estar vacío");
        }
        return value.trim();
    }
    
    public ClientId getId() {
        return id;
    }
    
    public UserId getUserId() {
        return userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public DocumentNumber getDocumentNumber() {
        return documentNumber;
    }
    
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
