package com.store.shoppingcart.clients.domain.exception;

import com.store.shoppingcart.clients.domain.model.DocumentNumber;

public class DuplicateDocumentException extends RuntimeException {
    
    public DuplicateDocumentException(DocumentNumber documentNumber) {
        super("Client already exists with document: " + documentNumber.value());
    }
}
