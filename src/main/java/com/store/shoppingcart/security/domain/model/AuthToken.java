package com.store.shoppingcart.security.domain.model;

import java.time.LocalDateTime;

public record AuthToken(String value, LocalDateTime expiresAt) {
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
