package com.store.shoppingcart.common.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    LocalDateTime timestamp,
    String message,
    List<String> details
) {
    public ErrorResponse(String message) {
        this(LocalDateTime.now(), message, null);
    }
    
    public ErrorResponse(String message, List<String> details) {
        this(LocalDateTime.now(), message, details);
    }
}
