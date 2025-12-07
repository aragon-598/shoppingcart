package com.store.shoppingcart.clients.domain.model;

import com.store.shoppingcart.clients.domain.exception.InvalidDocumentException;

public record DocumentNumber(String value, DocumentType type) {
    
    private static final int DUI_LENGTH = 9;
    private static final int NIT_LENGTH = 14;
    
    public DocumentNumber {
        validateDocumentNumber(value, type);
    }
    
    private void validateDocumentNumber(String value, DocumentType type) {
        if (value == null || value.isBlank()) {
            throw new InvalidDocumentException("Document number cannot be blank");
        }
        
        String cleaned = value.replaceAll("[^0-9]", "");
        
        switch (type) {
            case DUI -> {
                if (cleaned.length() != DUI_LENGTH) {
                    throw new InvalidDocumentException("DUI must have 9 digits");
                }
            }
            case NIT -> {
                if (cleaned.length() != NIT_LENGTH) {
                    throw new InvalidDocumentException("NIT must have 14 digits");
                }
            }
            case PASSPORT -> {
                if (cleaned.length() < 6 || cleaned.length() > 12) {
                    throw new InvalidDocumentException("Passport must have between 6 and 12 characters");
                }
            }
        }
    }
    
    public String formatted() {
        return switch (type) {
            case DUI -> formatDUI(value);
            case NIT -> formatNIT(value);
            case PASSPORT -> value;
        };
    }
    
    private String formatDUI(String value) {
        String cleaned = value.replaceAll("[^0-9]", "");
        return cleaned.substring(0, 8) + "-" + cleaned.substring(8);
    }
    
    private String formatNIT(String value) {
        String cleaned = value.replaceAll("[^0-9]", "");
        return cleaned.substring(0, 4) + "-" + cleaned.substring(4, 10) + "-" + 
               cleaned.substring(10, 13) + "-" + cleaned.substring(13);
    }
}
