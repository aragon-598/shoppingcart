package com.store.shoppingcart.clients.infrastructure.persistence.entity;

import com.store.shoppingcart.security.infrastructure.persistence.entity.UserJpaEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients", indexes = {
    @Index(name = "idx_document", columnList = "document_value,document_type", unique = true)
})
public class ClientEntity {
    
    @Id
    @Column(name = "id", length = 36)
    private String id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserJpaEntity user;
    
    @Column(name = "user_id", nullable = false, length = 36, unique = true, insertable = false, updatable = false)
    private String userId;
    
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    
    @Column(name = "document_value", nullable = false, length = 50)
    private String documentValue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 20)
    private DocumentTypeEntity documentType;
    
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;
    
    @Column(name = "street", nullable = false, length = 255)
    private String street;
    
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    
    @Column(name = "state", length = 100)
    private String state;
    
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    
    @Column(name = "country", nullable = false, length = 100)
    private String country;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public ClientEntity() {
    }
    
    public ClientEntity(String id, UserJpaEntity user, String firstName, String lastName, String documentValue,
                       DocumentTypeEntity documentType, String phoneNumber, String street, String city,
                       String state, String postalCode, String country, LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.userId = user != null ? user.getId() : null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentValue = documentValue;
        this.documentType = documentType;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public UserJpaEntity getUser() {
        return user;
    }
    
    public void setUser(UserJpaEntity user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getDocumentValue() {
        return documentValue;
    }
    
    public void setDocumentValue(String documentValue) {
        this.documentValue = documentValue;
    }
    
    public DocumentTypeEntity getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(DocumentTypeEntity documentType) {
        this.documentType = documentType;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
