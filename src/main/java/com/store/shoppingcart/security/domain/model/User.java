package com.store.shoppingcart.security.domain.model;

import java.time.LocalDateTime;

public class User {
    
    private UserId id;
    private Email email;
    private Password password;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;
    
    public User(UserId id, Email email, Password password, String firstName, 
                String lastName, Role role, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
    }
    
    public void activate() {
        this.active = true;
    }
    
    public void deactivate() {
        this.active = false;
    }
    
    public boolean hasRole(Role role) {
        return this.role == role;
    }
    
    public UserId getId() {
        return id;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public Password getPassword() {
        return password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public Role getRole() {
        return role;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
