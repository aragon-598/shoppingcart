package com.store.shoppingcart.clients.domain.exception;

import com.store.shoppingcart.clients.domain.model.DocumentNumber;

public class DuplicateDocumentException extends RuntimeException {
    
    public DuplicateDocumentException(DocumentNumber documentNumber) {
        super("Ya existe un cliente con el documento: " + documentNumber.value());
    }
}
